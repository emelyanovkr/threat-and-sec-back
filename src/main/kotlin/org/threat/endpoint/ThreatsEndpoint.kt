package org.threat.endpoint

import jakarta.enterprise.context.ApplicationScoped
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.threat.service.FetchThreatsService

@Path("/api")
@ApplicationScoped
class ThreatsEndpoint(private val fetchThreatsService: FetchThreatsService) {

    private val FETCH_THREATS_URL_FROM_FSTEC = "https://bdu.fstec.ru/files/documents/thrlist.xlsx"

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun getRelevantThreats() : Response {
        return Response.ok("Hello, something").build()
    }

    @GET
    @Path("/fetch-threats")
    @Produces(MediaType.APPLICATION_JSON)
    fun fetchThreatsFromBank() : Response
    {
        return try {
            val fetchedThreats = fetchThreatsService.acquireActualThreats(FETCH_THREATS_URL_FROM_FSTEC)
            Response.ok("Успешное обновление угроз - $fetchedThreats").build()
        } catch (e: Exception) {
            e.printStackTrace()
            Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.message).build()
        }
    }
}