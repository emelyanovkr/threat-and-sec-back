package org.threat.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.threat.dto.ThreatReportDTO
import org.threat.dto.risks.ConsequenceDTO
import org.threat.dto.risks.RiskDTO
import org.threat.dto.tactics.TacticDTO
import org.threat.dto.tactics.TechniqueDTO
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
import org.threat.model.threats.ThreatsExecutionMethod
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@ApplicationScoped
class FetchDataService {

    private val FIND_BY_ID_QUERY = "id in ?1"

    private fun getCategoryEntity(systemCategory: SystemCategory): SystemCategoryEntity? {
        return SystemCategoryEntity.find("name", systemCategory.name).firstResult()
    }

    @Transactional
    fun getRisksAndConsequences(systemCategory: SystemCategory): List<RiskDTO> {
        val risks = Risk.listAll().sortedBy { it.id }

        return risks.map { risk ->
            val consequences = Consequence.find(
                "SELECT cons " +
                        "FROM Consequence cons " +
                        "WHERE cons.risk = ?1 " +
                        "AND " +
                        "cons.systemCategory = ?2",
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
    fun getOffenders(): List<Offenders> {
        return Offenders.listAll().sortedBy { it.name }
    }

    fun getThreatsExecutionMethods(): List<ThreatsExecutionMethod> {
        return ThreatsExecutionMethod.listAll().sortedBy { it.id }
    }

    @Transactional
    fun getThreatReportFromDTO(threatReportDTO: ThreatReportDTO): ThreatReport {
        val threatReport = ThreatReport()
        threatReport.generalInformation = threatReportDTO.generalInformation
        threatReport.networkTable = threatReportDTO.networkTable

        threatReport.risksAndConsequencesDisplay = threatReportDTO.risksAndConsequences.flatMap { riskSelection ->
            Consequence.find(FIND_BY_ID_QUERY, riskSelection.consequenceIds)
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

        threatReport.violatorsInformationChosen =
            Offenders.find(FIND_BY_ID_QUERY, threatReportDTO.violatorsInformationChosenIds).list()

        threatReport.violatorsInformationExcluded =
            Offenders.find("id not in ?1", threatReportDTO.violatorsInformationChosenIds).list()

        threatReport.threatsExecutionMethods =
            ThreatsExecutionMethod.find(FIND_BY_ID_QUERY, threatReportDTO.threatsExecutionMethodsIds).list()

        threatReport.actualChosenThreats =
            ThreatInfo.find(FIND_BY_ID_QUERY, threatReportDTO.actualChosenThreatsIds).list()

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
    fun getAllTacticsAndTechniques(): List<TacticDTO> {
        val list: List<TacticToTechnique> = TacticToTechnique.findAll().list()
        return list
            .groupBy { it.tactic }
            .map { (tactic, links) ->
                TacticDTO(
                    tacticId = tactic.id!!,
                    tacticName = tactic.tacticName,
                    tacticalTask = tactic.tacticalTask,
                    techniques = links.map { link ->
                        link.technique.let { tech ->
                            TechniqueDTO(
                                id = tech.id!!,
                                techniqueNumber = tech.techniqueNumber,
                                techniqueDescription = tech.techniqueDescription
                            )
                        }
                    }
                )
            }
    }
}