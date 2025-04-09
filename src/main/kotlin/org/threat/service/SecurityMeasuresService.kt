package org.threat.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import org.threat.dto.SecurityClassRequest
import org.threat.model.general.input.SystemCategory
import org.threat.model.general.securityclass.*

@ApplicationScoped
class SecurityMeasuresService {

    private fun determineSecurityClass(request: SecurityClassRequest): Int {
        return when (request.systemCategory) {
            SystemCategory.GIS -> {
                requireNotNull(request.gisScale) { "ГИС: необходимо указать масштаб системы" }
                requireNotNull(request.gisSignificance) { "ГИС: необходимо указать значимость системы " }
                GisSecurityClass.findByOptions(request.gisScale!!, request.gisSignificance!!)?.securityClass
                    ?: throw NotFoundException("не найден класс защищенности для ГИС с параметрами ${request.gisScale} / ${request.gisSignificance}")
            }

            SystemCategory.ISPDN -> {
                requireNotNull(request.pdCategory) { "ИСПДН: необходимо указать категорию ИСПДН" }
                requireNotNull(request.ownWorker) { "ИСПДН: необходимо указать собственный работник или нет" }
                requireNotNull(request.subjectCount) { "ИСПДН: необходимо указать количество субъектов" }
                requireNotNull(request.threatType) { "ИСПДН: необходимо указать тип актуальных угроз" }
                IspdnSecurityClass.findByOptions(
                    request.pdCategory!!, request.ownWorker!!, request.subjectCount!!, request.threatType!!
                )?.securityClass
                    ?: throw IllegalArgumentException("не найден класс защищенности для ИСПДн с параметрами ${request.pdCategory} / ${request.ownWorker} / ${request.subjectCount} / ${request.threatType}")
            }

            SystemCategory.KII -> {
                requireNotNull(request.kiiSecurityClass) { "КИИ: необходимо указать полученный класс защищенности" }
                request.kiiSecurityClass!!
            }

            else -> throw IllegalArgumentException("неверно указан тип информационной системы: ${request.systemCategory}")

        }
    }

    @Transactional
    fun getDefensiveMeasures(request: SecurityClassRequest): List<BasicDefensiveMeasure> {
        val securityClass = determineSecurityClass(request)
        return BasicDefensiveMeasure.findBySecurityClass(securityClass)
    }

    @Transactional
    fun getDataSecurityTools(basicMeasuresIds: List<Long>): Map<BasicDefensiveMeasure, List<DataSecurityTool>> {
        val mappings = DefensiveMeasureToDataSecTool.find("measure.id in (?1)", basicMeasuresIds).list()
        return mappings.groupBy { it.measure!! }.mapValues { (_, mappingList) ->
            mappingList.mapNotNull { it.securityTool }
        }
    }
}