package com.techorgx.api.mapper

import com.fasterxml.jackson.databind.ObjectMapper
import com.techorgx.api.model.Payload
import org.springframework.stereotype.Component

@Component
class PayloadMapper(
    private val objectMapper: ObjectMapper
) {
    fun mapPayload(payload: String): Payload {
        return objectMapper.readValue(payload, Payload::class.java)
    }
}