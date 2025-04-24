package org.threat.endpoint

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.*
import jakarta.ws.rs.core.HttpHeaders
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import jakarta.ws.rs.core.StreamingOutput
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.threat.dto.ReportSummaryDTO
import org.threat.dto.ThreatReportDTO
import org.threat.model.ReportRequestHistory
import org.threat.model.general.SystemCategory
import org.threat.service.GenerateReportService
import org.threat.service.ReportManagementService
import java.time.LocalDate

@Path("/api")
@ApplicationScoped
class ReportEndpoint(
    private val generateReportService: GenerateReportService,

    @ConfigProperty(name = "report.template-name", defaultValue = "model-report")
    private val TEMPLATE_NAME: String,
    @ConfigProperty(name = "report.template-extension", defaultValue = ".docx")
    private val TEMPLATE_EXTENSION: String,
    private val objectMapper: ObjectMapper,
    private val reportManagementService: ReportManagementService
) {

    @POST
    @Path("/generate-model-report")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    fun generateModelReport(threatReportDTO: ThreatReportDTO): Response {
        val fileName = "${TEMPLATE_NAME}_${LocalDate.now()}$TEMPLATE_EXTENSION"
        val stream = StreamingOutput { os ->
            generateReportService.generateReport(threatReportDTO, os, true)
        }
        return Response.ok(stream)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"").build()
    }

    @GET
    @Path("/reports-history")
    @Produces(MediaType.APPLICATION_JSON)
    fun getReportsHistory(): Response {
        return Response.ok(ReportRequestHistory.listAll().sortedByDescending { it.createdAt }.map { history ->
            val sourceReport = objectMapper.readValue(history.payload, ThreatReportDTO::class.java)

            val reportSummaryDTO = ReportSummaryDTO(
                id = history.id!!,
                createdAt = history.createdAt,
                systemCategory = sourceReport.generalInformation.category,
            )

            when (reportSummaryDTO.systemCategory) {
                SystemCategory.GIS -> reportSummaryDTO.copy(
                    gisScale = sourceReport.generalInformation.gisSystemScale,
                    gisSignificance = sourceReport.generalInformation.gisSignificance,
                )

                SystemCategory.ISPDN -> reportSummaryDTO.copy(
                    ispdnCategory = sourceReport.generalInformation.ispdnCategory,
                    ispdnOwnWorker = sourceReport.generalInformation.ispdnOwnWorker,
                    ispdnThreatType = sourceReport.generalInformation.ispdnThreatType,
                    ispdnSubjectsCount = sourceReport.generalInformation.ispdnSubjectCount
                )

                SystemCategory.KII -> reportSummaryDTO.copy(
                    kiiSignificanceCategory = sourceReport.generalInformation.kiiSignificanceCategory,
                    kiiLevel = sourceReport.generalInformation.kiiLevel,
                    kiiSignificanceArea = sourceReport.generalInformation.kiiSignificanceArea,
                )

                SystemCategory.NONE -> sourceReport
            }
        }).build()
    }

    @DELETE
    @Path("/delete-report")
    fun deleteReport(@QueryParam("reportId") reportId: Long): Response {
        return Response.ok(reportManagementService.deleteReport(reportId)).build()
    }

    @GET
    @Path("/download-report")
    fun downloadSingleReport(@QueryParam("reportId") reportId: Long): Response {
        val fileName = "${TEMPLATE_NAME}_${LocalDate.now()}$TEMPLATE_EXTENSION"
        val stream = StreamingOutput { os ->
            reportManagementService.downloadSingleReport(reportId, os)
        }
        return Response.ok(stream)
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"$fileName\"").build()
    }
}