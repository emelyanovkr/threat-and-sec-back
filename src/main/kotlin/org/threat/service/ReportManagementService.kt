package org.threat.service

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.transaction.Transactional
import org.threat.dto.ThreatReportDTO
import org.threat.model.ReportRequestHistory
import java.io.OutputStream

@ApplicationScoped
class ReportManagementService(
    private val objectMapper: ObjectMapper,
    private val generateReportService: GenerateReportService
) {

    @Transactional
    fun deleteReport(reportId: Long): Boolean {
        return ReportRequestHistory.deleteById(reportId)
    }

    @Transactional
    fun downloadSingleReport(reportId: Long, outputStream: OutputStream) {
        val reportPayload = ReportRequestHistory.findById(reportId)?.payload
        val threatReportDTO = objectMapper.readValue(reportPayload, ThreatReportDTO::class.java)
        generateReportService.generateReport(threatReportDTO, outputStream, false)
    }
}