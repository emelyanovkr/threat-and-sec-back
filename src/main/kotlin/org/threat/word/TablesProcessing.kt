package org.threat.word

import org.docx4j.XmlUtils
import org.docx4j.jaxb.Context
import org.docx4j.openpackaging.packages.WordprocessingMLPackage
import org.docx4j.wml.*
import org.docx4j.wml.TcPrInner.VMerge
import org.threat.exception.NotFoundTablesInTemplate
import org.threat.exception.NotFoundTablesWithPlaceholders
import org.threat.model.ThreatReport
import org.threat.util.ReflectionUtils
import org.threat.util.ReflectionUtils.toMap

object TablesProcessing {

    private fun mergeTableColumns(table: Tbl, columnIndices: List<Int>) {
        val factory = Context.getWmlObjectFactory()

        val rows = table.content.filterIsInstance<Tr>()

        columnIndices.forEach { colIndex ->
            var previousText: String? = null

            rows.forEach { row ->
                val cells = row.content
                    .map { XmlUtils.unwrap(it) }
                    .filterIsInstance<Tc>()
                if (cells.size <= colIndex) return@forEach


                val cell = cells[colIndex]
                val currentText = getTextFromCell(cell)
                if (previousText == null || previousText != currentText) {
                    previousText = currentText
                    setVMerge(cell, "restart", factory)
                } else {
                    setVMerge(cell, "continue", factory)
                }
            }
        }
    }

    private fun setVMerge(cell: Tc, value: String, factory: ObjectFactory) {
        val tcPr = cell.tcPr ?: factory.createTcPr().also { cell.tcPr = it }
        val vMerge = VMerge()
        vMerge.`val` = if (value == "continue") null else value
        tcPr.vMerge = vMerge
    }

    private fun getTextFromCell(cell: Tc): String {
        val texts = ReflectionUtils.getAllElementsOfType(cell, Text::class.java)
        return texts.joinToString(separator = "") { it.value }
    }

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
        mergeColumns: List<Int>,
        transform: (T, Int) -> Map<String, String>,
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

        if (mergeColumns.isNotEmpty()) {
            mergeTableColumns(targetTable, mergeColumns)
        }
    }

    fun replaceVariablesInTable(wordProcessingPackage: WordprocessingMLPackage, threatReport: ThreatReport) {
        val documentElement = wordProcessingPackage.mainDocumentPart.jaxbElement
        val allTables = ReflectionUtils.getAllElementsOfType(documentElement, Tbl::class.java)
        if (allTables.isEmpty()) {
            throw NotFoundTablesInTemplate("No tables in template")
        }

        updateTableWithData(allTables, "\${indexNetwork}", threatReport.networkTable, listOf()) { entity, index ->
            entity.toMap() + ("indexNetwork" to (index + 1).toString().plus("."))
        }

        updateTableWithData(
            allTables,
            "\${riskCode}",
            threatReport.risksAndConsequencesDisplay,
            listOf(0, 1)
        ) { entity, _ ->
            entity.toDisplayMap()
        }

        updateTableWithData(allTables, "\${indexObject}", threatReport.influenceObjects, listOf()) { entity, index ->
            entity.toDisplayMap() + ("indexObject" to (index + 1).toString().plus("."))
        }

        updateTableWithData(
            allTables, "\${indexActualThreats}", threatReport.actualChosenThreats,
            listOf()
        ) { entity, index ->
            entity.toDisplayMap() + ("indexActualThreats" to (index + 1).toString().plus("."))
        }
    }
}