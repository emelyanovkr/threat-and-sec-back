package org.threat.endpoint

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.threat.dto.SecurityClassRequest
import org.threat.dto.ThreatReportDTO
import org.threat.model.general.SystemCategory
import org.threat.service.FetchDataService
import org.threat.service.GenerateReportService
import org.threat.service.RelevantThreatsService
import org.threat.service.SecurityMeasuresService

@Path("/api")
@ApplicationScoped
class ThreatsEndpoint(
    private val fetchDataService: FetchDataService,
    private val relevantThreatsService: RelevantThreatsService,
    private val generateReportService: GenerateReportService,
    private val securityMeasuresService: SecurityMeasuresService,
) {

    private val FETCH_THREATS_URL_FROM_FSTEC = "https://bdu.fstec.ru/files/documents/thrlist.xlsx"

    @GET
    @Path("/risks-consequences")
    @Produces(MediaType.APPLICATION_JSON)
    fun getRisksAndConsequences(@QueryParam("systemCategory") systemCategory: SystemCategory): Response {
        val risksAndConsequencesMap = fetchDataService.getRisksAndConsequences(systemCategory)
        return Response.ok(risksAndConsequencesMap).build()
    }

    @GET
    @Path("/offenders")
    @Produces(MediaType.APPLICATION_JSON)
    fun getOffenders(): Response {
        val offenders = fetchDataService.getOffenders()
        return Response.ok(offenders).build()
    }

    @GET
    @Path("/threats-execution")
    @Produces(MediaType.APPLICATION_JSON)
    fun getThreatsExecutionMethods(): Response {
        val executionMethods = fetchDataService.getThreatsExecutionMethods()
        return Response.ok(executionMethods).build()
    }

    @POST
    @Path("/relevant-threats")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getRelevantThreats(influenceObjects: List<String>): Response {
        val relevantThreats = relevantThreatsService.getRelevantThreats(influenceObjects)
        return Response.ok(relevantThreats).build()
    }

    @GET
    @Path("/tactics-techniques")
    @Produces(MediaType.APPLICATION_JSON)
    fun getTacticsAndTechniques(): Response {
        return Response.ok(fetchDataService.getAllTacticsAndTechniques()).build()
    }

    @POST
    @Path("/defensive-measures")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getBasicDefensiveMeasures(securityClassRequest: SecurityClassRequest): Response {
        return Response.ok(securityMeasuresService.getDefensiveMeasures(securityClassRequest)).build()
    }

    @POST
    @Path("/security-tools")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getDataSecurityTools(defensiveMeasuresIds: List<Long>): Response {
        return Response.ok(securityMeasuresService.getDataSecurityTools(defensiveMeasuresIds)).build()
    }

    @POST
    @Path("/generate-model-report")
    fun generateModelReport(threatReportDTO: ThreatReportDTO): Response {
        generateReportService.generateReport(threatReportDTO)
        return Response.ok().build()
    }

    @GET
    @Path("/fetch-threats")
    @Produces(MediaType.APPLICATION_JSON)
    fun fetchThreatsFromBank(): Response {
        return try {
            val fetchedThreats = fetchDataService.acquireActualThreats(FETCH_THREATS_URL_FROM_FSTEC)
            Response.ok("Успешное обновление угроз - $fetchedThreats").build()
        } catch (e: Exception) {
            e.printStackTrace()
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.message).build()
        }
    }
}