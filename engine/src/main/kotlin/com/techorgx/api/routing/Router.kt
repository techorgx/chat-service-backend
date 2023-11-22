package com.techorgx.api.routing

import com.techorgx.api.service.RedisService
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Controller


@Controller
class Router(
    private val redisService: RedisService
) {
    @MessageMapping("/send-message")
    fun handleMessage (@Payload payload: String) {
        logger.info("Request received on method $SEND_MESSAGE, Payload: $payload")
        redisService.publishMessage(payload)
    }

    private companion object {
        val logger: Logger = LogManager.getLogger(Router::class.qualifiedName)
        const val SEND_MESSAGE = "/send-message"
    }
}
