package org.threat.util

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import jakarta.enterprise.context.ApplicationScoped

object StaticObjectMapper {
    @ApplicationScoped
    fun objectMapper(): ObjectMapper = jacksonObjectMapper().registerKotlinModule()
}