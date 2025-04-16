package org.threat.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.threat.dto.ThreatReportDTO
import org.threat.dto.risks.ConsequenceDTO
import org.threat.dto.risks.RiskDTO
import org.threat.excel.ExcelOperations
import org.threat.exception.FetchThreatsException
import org.threat.model.ThreatReport
import org.threat.model.general.DataReplacementEntity
import org.threat.model.general.SystemCategory
import org.threat.model.general.SystemCategoryEntity
import org.threat.model.offenders.Offenders
import org.threat.model.risks.Consequence
import org.threat.model.risks.Risk
import org.threat.model.risks.RiskAndConsequenceDisplay
import org.threat.model.tactics.TacticToTechnique
import org.threat.model.threats.ThreatInfo
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@ApplicationScoped
class FetchDataService {

    private fun getCategoryEntity(systemCategory: SystemCategory): SystemCategoryEntity? {
        return SystemCategoryEntity.find("name", systemCategory.name).firstResult()
    }

    @Transactional
    fun getRisksAndConsequences(systemCategory: SystemCategory): List<RiskDTO> {
        val risks = Risk.listAll()

        return risks.map { risk ->
            val consequences = Consequence.find(
                "SELECT cons " +
                        "FROM Consequence cons " +
                        "WHERE cons.risk = ?1 " +
                        "AND " +
                        "cons.systemCategory = ?2 ",
                risk,
                getCategoryEntity(systemCategory)!!
            ).list().map { cons ->
                ConsequenceDTO(
                    id = cons.id!!,
                    title = cons.title
                )
            }.toSet()

            RiskDTO(
                id = risk.id!!,
                riskCode = risk.code,
                riskName = risk.name,
                consequences = consequences
            )
        }
    }

    @Transactional
    fun getThreatReportFromDTO(threatReportDTO: ThreatReportDTO): ThreatReport {
        val threatReport = ThreatReport()
        threatReport.generalInformation = threatReportDTO.generalInformation
        threatReport.networkTable = threatReportDTO.networkTable

        threatReport.risksAndConsequencesDisplay = threatReportDTO.risksAndConsequences.flatMap { riskSelection ->
            Consequence.find("id in ?1", riskSelection.consequenceIds)
                .list()
                .map { consequence ->
                    RiskAndConsequenceDisplay(
                        riskCode = consequence.risk.code,
                        riskName = consequence.risk.name,
                        consequenceTitle = consequence.title,
                        consequenceDescription = consequence.description
                    )
                }
        }

        threatReport.influenceObjects = threatReportDTO.influenceObjects
        threatReport.violatorsInformationChosen = threatReportDTO.violatorsInformationChosen
        threatReport.violatorsInformationExcluded =
            Offenders.find("name not in ?1", threatReportDTO.violatorsInformationChosen.map { it.name }).list()
        threatReport.threatsExecutionMethods = threatReportDTO.threatsExecutionMethods

        threatReport.actualChosenThreats =
            ThreatInfo.find("id in ?1", threatReportDTO.actualChosenThreats.map { it.id }).list()

        return threatReport
    }

    @Transactional
    fun getReplacementForPlaceholdersOfCategory(systemCategory: SystemCategory): Map<String, String> {
        val systemCategoryEntity = getCategoryEntity(systemCategory)
        val replacements = DataReplacementEntity.find("category", systemCategoryEntity!!).list()
        val placeholdersMap = replacements.associate { it.placeholder to it.value }

        return placeholdersMap
    }

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