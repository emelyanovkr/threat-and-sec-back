package org.threat.service

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.xml.bind.JAXBElement
import org.docx4j.XmlUtils
import org.docx4j.model.datastorage.migration.VariablePrepare
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.org.apache.xml.serializer.utils.ResourceUtils
import org.docx4j.wml.*
import org.threat.exception.NotFoundTablesInTemplate
import org.threat.exception.NotFoundTablesWithPlaceholders
import org.threat.model.report.ThreatReport
import java.io.File
import java.time.LocalDate
import kotlin.reflect.full.memberProperties

@ApplicationScoped
class GenerateReportService {
    private val TEMPLATE_NAME = "Template"
    private val TEMPLATE_EXTENSION = ".docx"
    private val TEMPLATE_FULL_NAME = TEMPLATE_NAME + TEMPLATE_EXTENSION

    private fun <T> getAllElementsOfType(obj: Any, toSearch: Class<T>): List<T> {
        val result = mutableListOf<T>()

        val realObj = if (obj is JAXBElement<*>) obj.value else obj

        if (toSearch.isInstance(realObj)) {
            result.add(toSearch.cast(realObj))
        }
        if (realObj is ContentAccessor) {
            for (child in realObj.content) {
                result.addAll(getAllElementsOfType(child, toSearch))
            }
        }
        return result
    }

    private fun tableContainPlaceholder(table: Tbl, placeholder: String): Boolean {
        val texts = getAllElementsOfType(table, Text::class.java)
        return texts.any { it.value.contains(placeholder) }
    }

    private fun <T : Any> T.toMap(): Map<String, String> {
        return this::class.memberProperties.associate { prop ->
            prop.name to (prop.getter.call(this)?.toString() ?: "")
        }
    }

    private fun variableReplaceInRow(row: Tr, replacements: Map<String, String>) {
        val texts = getAllElementsOfType(row, Text::class.java)
        for (text in texts) {
            var textValue = text.value
            replacements.forEach { (key, value) ->
                val placeholder = "\${$key}"
                if (textValue.contains(placeholder)) {
                    textValue = textValue.replace(placeholder, value)
                }
            }
            text.value = textValue
        }
    }

    private fun replaceVariablesInTable(wordProcessingPackage: WordprocessingMLPackage, threatReport: ThreatReport) {
        val documentElement = wordProcessingPackage.mainDocumentPart.jaxbElement
        val allTables = getAllElementsOfType(documentElement, Tbl::class.java)

        if (allTables.isEmpty()) {
            throw NotFoundTablesInTemplate("No tables in template")
        }

        val tablesWithPlaceholders = allTables.filter { tableContainPlaceholder(it, "\${index}") }
        if (tablesWithPlaceholders.isEmpty()) {
            throw NotFoundTablesWithPlaceholders("Not found tables with placeholders in template")
        }

        val table = tablesWithPlaceholders.first()
        val templateRow = table.content[1] as Tr

        table.content.removeAt(1)

        threatReport.networkTable?.forEachIndexed { index, entity ->
            val newRow = XmlUtils.deepCopy(templateRow) as Tr

            val rowData = entity.toMap() + ("index" to (index + 1).toString())
            variableReplaceInRow(newRow, rowData)

            table.content.add(newRow)
        }
    }

    fun generateReport(threatReport: ThreatReport) {
        val templateFile = ResourceUtils.getResource(TEMPLATE_FULL_NAME)
        val wordProcessingPackage = WordprocessingMLPackage.load(templateFile)

        VariablePrepare.prepare(wordProcessingPackage)

        replaceVariablesInTable(wordProcessingPackage, threatReport)

        wordProcessingPackage.mainDocumentPart.variableReplace(threatReport.generalInformation?.toMap())

        val outputFile = File("src/main/resources/generated/${TEMPLATE_NAME}_${LocalDate.now()}$TEMPLATE_EXTENSION")
        if (!outputFile.parentFile.exists()) {
            outputFile.parentFile.mkdirs()
        }
        Log.info("File successfully generated ${outputFile.absolutePath}")
        wordProcessingPackage.save(outputFile)
    }
}