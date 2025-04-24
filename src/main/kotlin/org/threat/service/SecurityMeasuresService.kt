package org.threat.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import org.threat.dto.SecurityClassRequest
import org.threat.model.general.SystemCategory
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
                requireNotNull(request.ispdnCategory) { "ИСПДН: необходимо указать категорию ИСПДН" }
                requireNotNull(request.ispdnOwnWorker) { "ИСПДН: необходимо указать собственный работник или нет" }
                requireNotNull(request.ispdnSubjectCount) { "ИСПДН: необходимо указать количество субъектов" }
                requireNotNull(request.ispdnThreatType) { "ИСПДН: необходимо указать тип актуальных угроз" }
                IspdnSecurityClass.findByOptions(
                    request.ispdnCategory!!, request.ispdnOwnWorker!!, request.ispdnSubjectCount!!, request.ispdnThreatType!!
                )?.securityClass
                    ?: throw IllegalArgumentException("не найден класс защищенности для ИСПДн с параметрами ${request.ispdnCategory} / ${request.ispdnOwnWorker} / ${request.ispdnSubjectCount} / ${request.ispdnThreatType}")
            }

            SystemCategory.KII -> {
                requireNotNull(request.kiiSignificanceCategory) { "КИИ: необходимо указать полученную категорию значимости" }
                request.kiiSignificanceCategory!!
            }

            else -> throw IllegalArgumentException("неверно указан тип информационной системы: ${request.systemCategory}")

        }
    }

    @Transactional
    fun getDefensiveMeasures(request: SecurityClassRequest): List<BasicDefensiveMeasure> {
        val securityClass = determineSecurityClass(request)
        return BasicDefensiveMeasure.findBySecurityClassAndSystemCategory(securityClass, request.systemCategory)
    }

    @Transactional
    fun getDataSecurityTools(basicMeasuresIds: List<Long>): Map<BasicDefensiveMeasure, List<DataSecurityTool>> {
        val mappings = DefensiveMeasureToDataSecTool.find("measure.id in (?1)", basicMeasuresIds).list()
        return mappings.groupBy { it.measure!! }.mapValues { (_, mappingList) ->
            mappingList.mapNotNull { it.securityTool }
        }
    }
}