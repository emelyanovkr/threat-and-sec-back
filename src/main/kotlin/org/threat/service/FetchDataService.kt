package org.threat.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.threat.excel.ExcelOperations
import org.threat.exception.FetchThreatsException
import org.threat.model.tactics.TacticToTechnique
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@ApplicationScoped
class FetchDataService {

    @Transactional
    fun acquireActualThreats(fetchThreatsUrl: String): Int {
        val client = HttpClient.newHttpClient()
        val request = HttpRequest.newBuilder().uri(URI.create(fetchThreatsUrl)).build()

        val response = client.send(request, HttpResponse.BodyHandlers.ofByteArray())

        if (response.statusCode() == 200) {
            return ExcelOperations.parseExcelFile(response.body())
        } else {
            throw FetchThreatsException("Не удалось получить данные об угрозах, статус - ${response.statusCode()}")
        }
    }

    @Transactional
    fun getAllTacticsAndTechniques(): List<TacticToTechnique> = TacticToTechnique.findAll().list()
}