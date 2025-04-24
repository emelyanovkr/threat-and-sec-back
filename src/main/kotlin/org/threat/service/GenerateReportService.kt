package org.threat.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.inject.Default
import jakarta.transaction.Transactional
import org.docx4j.model.datastorage.migration.VariablePrepare
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.org.apache.xml.serializer.utils.ResourceUtils
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.threat.dto.ThreatReportDTO
import org.threat.model.ReportRequestHistory
import org.threat.model.ThreatReport
import org.threat.model.offenders.OffendersType
import org.threat.model.threats.DataInsertionType
import org.threat.model.threats.InfluenceObject
import org.threat.model.threats.ThreatInfo
import org.threat.util.ReflectionUtils.toMap
import org.threat.word.ProceedDataBulletedInsert
import org.threat.word.ProceedOffendersInsert
import org.threat.word.TablesProcessing
import java.io.File
import java.io.OutputStream
import java.time.LocalDate
import java.time.OffsetDateTime

@ApplicationScoped
class GenerateReportService(
    var fetchDataService: FetchDataService,
    @ConfigProperty(name = "report.template-name", defaultValue = "model-report")
    private val TEMPLATE_NAME: String,
    @ConfigProperty(name = "report.template-extension", defaultValue = ".docx")
    private val TEMPLATE_EXTENSION: String,
    private val objectMapper: ObjectMapper
) {

    private val TEMPLATE_FULL_NAME = TEMPLATE_NAME + TEMPLATE_EXTENSION

    private fun calculateParams(currentParams: Map<String, String>): Map<String, String> {
        val regex = Regex("""\$\{([^}]+)}""")
        val newParams = currentParams.mapValues { (_, value) ->
            regex.replace(value) { match ->
                val key = match.groupValues[1]
                currentParams[key] ?: match.value
            }
        }
        return if (newParams == currentParams) newParams else calculateParams(newParams)
    }

    private fun fetchOverallDataReplacement(threatReport: ThreatReport): Map<String, String> {
        val replacementPlaceholders =
            fetchDataService.getReplacementForPlaceholdersOfCategory(threatReport.generalInformation.category)

        val overallMapForReplacement = replacementPlaceholders + threatReport.generalInformation.toMap()
        return calculateParams(overallMapForReplacement)
    }

    private fun fetchInfluenceObjectCategoriesForActualThreats(threatReport: ThreatReport): List<String> {
        val excludedThreats = ThreatInfo.find("id not in ?1", threatReport.actualChosenThreats.map { it.id }).list()

        val threatsCategoriesWithActualThreats: MutableMap<InfluenceObject, List<ThreatInfo>> = mutableMapOf()
        excludedThreats.forEach { threat ->
            val foundInfluenceObject = InfluenceObject.findInfluenceObjectsCategory(threat).toList()
            foundInfluenceObject.forEach { influenceObject ->
                threatsCategoriesWithActualThreats[influenceObject] =
                    (threatsCategoriesWithActualThreats[influenceObject] ?: emptyList()) + threat
            }
        }

        return threatsCategoriesWithActualThreats.map { "${it.key.name} (${it.value.joinToString(", ") { threat -> "УБИ.${threat.id}" }})" }
    }

    /*
        ${riskCode}:List<Consequences> split by .
     */
    @Transactional
    fun generateReport(threatReportDTO: ThreatReportDTO, outputStream: OutputStream, persistGeneratedReport: Boolean) {
        val templateFile = ResourceUtils.getResource(TEMPLATE_FULL_NAME)
        val wordProcessingPackage = WordprocessingMLPackage.load(templateFile)

        VariablePrepare.prepare(wordProcessingPackage)

        val threatReport = fetchDataService.getThreatReportFromDTO(threatReportDTO)

        // обработка данных внутри таблиц
        TablesProcessing.replaceVariablesInTable(wordProcessingPackage, threatReport)

        // вставка данных о не выбранных нарушителях
        ProceedOffendersInsert.insertViolatorsInformation(
            wordProcessingPackage, threatReport.violatorsInformationExcluded, OffendersType.EXCLUDED
        )

        // вставка данных о выбранных нарушителях
        ProceedOffendersInsert.insertViolatorsInformation(
            wordProcessingPackage, threatReport.violatorsInformationChosen, OffendersType.CHOSEN
        )

        // вставка способов реализации угроз
        ProceedDataBulletedInsert.insertBulletedData(
            wordProcessingPackage,
            threatReport.threatsExecutionMethods.map { it.name },
            DataInsertionType.THREATS_METHODS
        )

        val actualExcludedThreatsFormattedList = fetchInfluenceObjectCategoriesForActualThreats(threatReport)

        // вставка неактуальных УБИ по категориям объектов воздействия
        ProceedDataBulletedInsert.insertBulletedData(
            wordProcessingPackage, actualExcludedThreatsFormattedList, DataInsertionType.ACTUAL_THREATS
        )

        val overallDataParamsMap = fetchOverallDataReplacement(threatReport)

        // замена остальных параметров по всему документу
        wordProcessingPackage.mainDocumentPart.variableReplace(overallDataParamsMap)

        // todo: temporary for debugging
        val outputFile = File("src/main/resources/generated/${TEMPLATE_NAME}_${LocalDate.now()}$TEMPLATE_EXTENSION")
        if (!outputFile.parentFile.exists()) {
            outputFile.parentFile.mkdirs()
        }
        Log.info("File successfully generated ${outputFile.absolutePath}")
        wordProcessingPackage.save(outputFile)
        wordProcessingPackage.save(outputStream)

        if (persistGeneratedReport) {
            val reportRequestJson = objectMapper.writeValueAsString(threatReportDTO)
            val reportRequest =
                ReportRequestHistory(
                    OffsetDateTime.now(),
                    reportRequestJson,
                    threatReportDTO.generalInformation.category
                )
            reportRequest.persist()
            Log.info("Saving report to history: ${reportRequest.id}")
        }
    }
}