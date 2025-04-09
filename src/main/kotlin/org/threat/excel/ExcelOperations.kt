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

    /*private fun isCellGreen(cell: Cell): Boolean {
        val cellStyle: CellStyle = cell.cellStyle ?: return false

        // Проверяем ForegroundColorColor (для XSSFCellStyle)
        val xssfStyle = cellStyle as? XSSFCellStyle ?: return false
        val fgColor: XSSFColor? = xssfStyle.fillForegroundColorColor as? XSSFColor

        // Примерный способ сравнить c определенным RGB (здесь стоит подставить реальный RGB "зелёного" в файле)
        val greenRgb = byteArrayOf(147.toByte(), 196.toByte(), 125.toByte()) // это "зелёный" (пример из Excel)
        return fgColor?.rgb?.contentEquals(greenRgb) == true
    }

    fun parseExcel(): Response {
        try {
            val file = File("C:\\Users\\Kirill\\Desktop\\Lika Diploma's Project\\Docs\\АБс-022_Итоговая таблица.xlsx")

            // Открываем Excel-файл с помощью Apache POI
            val workbook = WorkbookFactory.create(file)
            val sheet = workbook.getSheetAt(0)

            // ==============================
            // 1. Считываем названия СЗИ из 3-й строки (rowIndex = 2), начиная с колонки 3 (colIndex = 2)
            // ==============================
            val toolRowIndex = 2 // третья строка
            val toolRow = sheet.getRow(toolRowIndex)
                ?: throw RuntimeException("Не найдена строка с инструментами (rowIndex = $toolRowIndex)")

            // Собираем мапу: номер колонки -> имя СЗИ
            val columnToToolName = mutableMapOf<Int, String>()
            var colIndex = 2 // в Excel столбец C = индекс 2
            while (true) {
                val cell = toolRow.getCell(colIndex) ?: break
                val value = cell.stringCellValue?.trim() ?: break
                if (value.isBlank()) break

                columnToToolName[colIndex] = value
                colIndex++
            }

            // ==============================
            // 2. Перебираем меры защиты с 4-й строки (rowIndex = 3), берем их из первого столбца (colIndex = 0)
            // ==============================
            for (rowIndex in 3..sheet.lastRowNum) {
                val row = sheet.getRow(rowIndex) ?: continue
                val measureCell = row.getCell(0) ?: continue

                // Считываем ключ/название меры (пример: "ИАФ.1")
                val measureKey = measureCell.stringCellValue?.trim() ?: continue
                if (measureKey.isEmpty()) continue

                // Пытаемся найти меру в БД
                val measure = BasicDefensiveMeasure.find("key", measureKey).firstResult()
                    ?: throw RuntimeException("Мера защиты '$measureKey' не найдена в БД")

                // ==============================
                // 3. Смотрим соответствие по цвету в ячейках, начиная со столбца 3 (колонка C, index=2)
                // ==============================
                for ((currentColIndex, toolName) in columnToToolName) {
                    val cell = row.getCell(currentColIndex) ?: continue

                    if (isCellGreen(cell)) {
                        // Значит, ячейка закрашена "зелёным", и мы мапим текущую меру к этому средству
                        val securityTool = DataSecurityTool.find("name", toolName).firstResult()
                            ?: throw RuntimeException("Инструмент защиты '$toolName' не найден в БД")

                        // Создаем и сохраняем связь
                        val mapping = DefensiveMeasureToDataSecTool().apply {
                            this.measure = measure
                            this.securityTool = securityTool
                        }
                        mapping.persist()
                    }
                }
            }

            workbook.close()
            Response.ok("Импорт успешно завершён").build()

        } catch (ex: Exception) {
            ex.printStackTrace()
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Ошибка при импорте: ${ex.message}")
                .build()
        }
        return Response.ok().build()
    }*/
}