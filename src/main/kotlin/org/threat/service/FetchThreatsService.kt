package org.threat.service

import io.quarkus.logging.Log
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.LoggerFactory
import org.threat.exception.FetchThreatsException
import org.threat.model.InfluenceObject
import org.threat.model.ThreatInfo
import org.threat.model.ThreatToInfluenceObject
import java.io.ByteArrayInputStream
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*

@ApplicationScoped
class FetchThreatsService {

    private fun parseExcelFile(fileBytes: ByteArray): Int {
        val workBook = XSSFWorkbook(ByteArrayInputStream(fileBytes))
        val sheet = workBook.getSheetAt(0)

        var threatsCount = 0

        for (row in sheet.drop(2)) {
            val threatId = row.getCell(0).numericCellValue.toLong()
            val name = row.getCell(1)?.stringCellValue ?: continue
            val description = row.getCell(2)?.stringCellValue ?: ""
            val objectsRaw = row.getCell(4)?.stringCellValue ?: ""
            val objectsList =
                objectsRaw.split(",").map { objectName ->
                    objectName.trim().replaceFirstChar { it.titlecase(Locale.forLanguageTag("ru-RU")) }
                }

            val threat = ThreatInfo.findById(threatId) ?: ThreatInfo(name = name, description = description).apply {
                id = threatId
                persist()
            }


            for (objectName in objectsList) {
                val influenceObject = InfluenceObject.findByName(objectName)
                if (influenceObject != null) {
                    val relation = ThreatToInfluenceObject(threat = threat, influenceObject = influenceObject)
                    relation.persist()
                    threatsCount++
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
}