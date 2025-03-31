package org.threat.exception

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class GenerateTablesExceptionMapper : ExceptionMapper<Exception> {
    override fun toResponse(exception: Exception): Response {
        return when (exception) {
            is NotFoundTablesInTemplate,
            is NotFoundTablesWithPlaceholders -> Response.status(Response.Status.NOT_FOUND)
                .entity("Ошибка при обновлении данных об угрозах: ${exception.message}")
                .build()

            else -> Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Необработанная ошибка: ${exception.message}")
                .build()
        }
    }
}