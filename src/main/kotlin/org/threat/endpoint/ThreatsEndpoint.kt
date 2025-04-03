package org.threat.endpoint

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.*
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.threat.model.ThreatReport
import org.threat.service.FetchDataService
import org.threat.service.GenerateReportService
import org.threat.service.RelevantThreatsService

@Path("/api")
@ApplicationScoped
class ThreatsEndpoint(
    private val fetchDataService: FetchDataService,
    private val relevantThreatsService: RelevantThreatsService,
    private val generateReportService: GenerateReportService
) {

    private val FETCH_THREATS_URL_FROM_FSTEC = "https://bdu.fstec.ru/files/documents/thrlist.xlsx"

    @POST
    @Path("/relevant-threats")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    fun getRelevantThreats(influenceObjects: List<String>): Response {
        val relevantThreats = relevantThreatsService.getRelevantThreats(influenceObjects)
        return Response.ok(relevantThreats).build()
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

    @GET
    @Path("/fetch-tactics")
    @Produces(MediaType.APPLICATION_JSON)
    fun fetchAllTacticsAndTechniques(): Response {
        return Response.ok(fetchDataService.getAllTacticsAndTechniques()).build()
    }

    @POST
    @Path("/generate-model-report")
    fun generateModelReport(threatReport: ThreatReport): Response {
        generateReportService.generateReport(threatReport)
        return Response.ok().build()
    }
}