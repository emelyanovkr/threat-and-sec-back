package org.threat.excel

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.threat.model.threats.InfluenceObject
import org.threat.model.threats.ThreatInfo
import org.threat.model.threats.ThreatToInfluenceObject
import java.io.ByteArrayInputStream
import java.util.*

object ExcelOperations {

    fun parseExcelFile(fileBytes: ByteArray): Int {
        val workBook = XSSFWorkbook(ByteArrayInputStream(fileBytes))
        val sheet = workBook.getSheetAt(0)

        var threatsCount = 0

        for (row in sheet.drop(2)) {
            val threatId = row.getCell(0).numericCellValue.toLong()
            val name = row.getCell(1)?.stringCellValue ?: continue
            val description = row.getCell(2)?.stringCellValue ?: ""
            val objectsRaw = row.getCell(4)?.stringCellValue ?: ""

            val specialPattern = Regex(
                "(;)" +
                        "|(Программное\\s+обеспечение\\s*\\(программы\\),)" +
                        "|(,\\s*реализующие)" +
                        "|(,\\s*использующее\\s+)" +
                        "|(\\([^()]*,[^()]*\\))"
            )

            val objectsList = if (specialPattern.containsMatchIn(objectsRaw)) {
                objectsRaw.split(";").map { objectName ->
                    objectName.trim().replaceFirstChar { it.titlecase(Locale.forLanguageTag("ru-RU")) }
                }
            } else {
                objectsRaw.split(",").map { objectName ->
                    objectName.trim().replaceFirstChar { it.titlecase(Locale.forLanguageTag("ru-RU")) }
                }
            }

            val threat = ThreatInfo.findById(threatId) ?: ThreatInfo(name = name, description = description).apply {
                id = threatId
                persist()
            }

            for (objectName in objectsList) {
                val influenceObject = InfluenceObject.findByNameOrAlias(objectName)
                if (influenceObject != null) {
                    val existingRelation = ThreatToInfluenceObject.findExisting(threat, influenceObject)
                    if (existingRelation == null) {
                        val relation = ThreatToInfluenceObject(threat = threat, influenceObject = influenceObject)
                        relation.persist()
                        threatsCount++
                    }
                } else {
                    println("Не найден объект воздействия с именем: $objectName для угрозы $threatId - ${threat.name}")
                }
            }
        }

        workBook.close()
        return threatsCount
    }
}