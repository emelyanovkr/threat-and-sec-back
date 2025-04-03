package org.threat.service

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import org.docx4j.model.datastorage.migration.VariablePrepare
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.org.apache.xml.serializer.utils.ResourceUtils
import org.threat.model.ThreatReport
import org.threat.util.ReflectionUtils.toMap
import org.threat.word.PrepareOffenders
import org.threat.word.TablesProcessing
import java.io.File
import java.time.LocalDate

@ApplicationScoped
class GenerateReportService(var fetchDataService: FetchDataService) {
    private val TEMPLATE_NAME = "Template"
    private val TEMPLATE_EXTENSION = ".docx"
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

    fun generateReport(threatReport: ThreatReport) {
        val replacementPlaceholders =
            fetchDataService.getReplacementForPlaceholdersOfCategory(threatReport.generalInformation.category)

        val templateFile = ResourceUtils.getResource(TEMPLATE_FULL_NAME)
        val wordProcessingPackage = WordprocessingMLPackage.load(templateFile)

        VariablePrepare.prepare(wordProcessingPackage)

        // обработка данных внутри таблиц
        TablesProcessing.replaceVariablesInTable(wordProcessingPackage, threatReport)

        PrepareOffenders.insertViolatorsInformation(wordProcessingPackage, threatReport.violatorsInformation)

        val overallMapForReplacement =
            replacementPlaceholders + threatReport.generalInformation.toMap()
        val finalParamsMap = calculateParams(overallMapForReplacement)

        // замена остальных параметров по всему документу
        wordProcessingPackage.mainDocumentPart.variableReplace(finalParamsMap)

        val outputFile = File("src/main/resources/generated/${TEMPLATE_NAME}_${LocalDate.now()}$TEMPLATE_EXTENSION")
        if (!outputFile.parentFile.exists()) {
            outputFile.parentFile.mkdirs()
        }
        Log.info("File successfully generated ${outputFile.absolutePath}")
        wordProcessingPackage.save(outputFile)
    }
}