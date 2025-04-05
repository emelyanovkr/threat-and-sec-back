package org.threat.word

import org.apache.poi.ss.formula.functions.T
import org.docx4j.XmlUtils
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.Tbl
import org.docx4j.wml.Text
import org.docx4j.wml.Tr
import org.threat.exception.NotFoundTablesInTemplate
import org.threat.exception.NotFoundTablesWithPlaceholders
import org.threat.model.ThreatReport
import org.threat.util.ReflectionUtils
import org.threat.util.ReflectionUtils.toMap

object TablesProcessing {

    private fun tableContainPlaceholder(table: Tbl, placeholder: String): Boolean {
        val texts = ReflectionUtils.getAllElementsOfType(table, Text::class.java)
        return texts.any { it.value.contains(placeholder) }
    }

    private fun variableReplaceInRow(row: Tr, replacements: Map<String, String>) {
        val texts = ReflectionUtils.getAllElementsOfType(row, Text::class.java)
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

    private fun <T> updateTableWithData(
        allTables: List<Tbl>,
        placeholder: String,
        data: List<T>,
        transform: (T, Int) -> Map<String, String>
    ) {
        val targetTable = allTables.find { tableContainPlaceholder(it, placeholder) }
            ?: throw NotFoundTablesWithPlaceholders("Not found table with placeholder $placeholder")
        val templateRow = targetTable.content[1] as Tr
        targetTable.content.removeAt(1)
        data.forEachIndexed { index, entity ->
            val newRow = XmlUtils.deepCopy(templateRow) as Tr
            val rowData = transform(entity, index)
            variableReplaceInRow(newRow, rowData)
            targetTable.content.add(newRow)
        }
    }

    fun replaceVariablesInTable(wordProcessingPackage: WordprocessingMLPackage, threatReport: ThreatReport) {
        val documentElement = wordProcessingPackage.mainDocumentPart.jaxbElement
        val allTables = ReflectionUtils.getAllElementsOfType(documentElement, Tbl::class.java)
        if (allTables.isEmpty()) {
            throw NotFoundTablesInTemplate("No tables in template")
        }

        updateTableWithData(allTables, "\${indexNetwork}", threatReport.networkTable) { entity, index ->
            entity.toMap() + ("indexNetwork" to (index + 1).toString().plus("."))
        }

        updateTableWithData(allTables, "\${indexObject}", threatReport.influenceObjects) { entity, index ->
            entity.toDisplayMap() + ("indexObject" to (index + 1).toString().plus("."))
        }

        updateTableWithData(allTables, "\${indexActualThreats}", threatReport.actualChosenThreats) { entity, index ->
            entity.toDisplayMap() + ("indexActualThreats" to (index + 1).toString().plus("."))
        }
    }

}