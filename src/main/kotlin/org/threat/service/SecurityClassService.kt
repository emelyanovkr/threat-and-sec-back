package org.threat.service

import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import jakarta.ws.rs.NotFoundException
import org.threat.dto.SecurityClassRequest
import org.threat.model.general.SystemCategory
import org.threat.model.general.securityclass.BasicDefensiveMeasure
import org.threat.model.general.securityclass.GisSecurityClass
import org.threat.model.general.securityclass.IspdnSecurityClass

@ApplicationScoped
class SecurityClassService {

    private fun determineSecurityClass(request: SecurityClassRequest): Int {
        return when (request.systemCategory) {
            SystemCategory.GIS -> {
                requireNotNull(request.gisScale) { "ГИС: необходимо указать масштаб системы" }
                requireNotNull(request.gisSignificanceOptions) { "ГИС: необходимо указать значимость системы " }
                GisSecurityClass.findByOptions(request.gisScale!!, request.gisSignificanceOptions!!)?.securityClass
                    ?: throw NotFoundException("не найден класс защищенности для ГИС с параметрами ${request.gisScale} / ${request.gisSignificanceOptions}")
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
}