package org.threat.exception

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class FetchExceptionMapper : ExceptionMapper<FetchThreatsException> {
    override fun toResponse(exception: FetchThreatsException): Response {
        return Response.status(Response.Status.BAD_GATEWAY)
            .entity("Ошибка при обновлении данных об угрозах: ${exception.message}").build()
    }
}