package org.threat.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.threat.exception.FetchThreatsException
import org.threat.model.*
import java.io.ByteArrayInputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

@ApplicationScoped
class FetchDataService {

    private fun parseExcelFile(fileBytes: ByteArray): Int {
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

    @Transactional
    fun acquireActualThreats(fetchThreatsUrl: String): Int {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder().uri(URI.create(fetchThreatsUrl)).build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofByteArray())

        if (response.statusCode() == 200) {
            return parseExcelFile(response.body())
        } else {
            throw FetchThreatsException("Не удалось получить данные об угрозах, статус - ${response.statusCode()}")
        }
    }

    @Transactional
    fun getAllTacticsAndTechniques(): List<TacticToTechnique> = TacticToTechnique.findAll().list()
}