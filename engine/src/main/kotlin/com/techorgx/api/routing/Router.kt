package com.techorgx.api.routing

import com.techorgx.api.service.RedisService
import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller


@Controller
class Router(
    private val redisService: RedisService
) {
    @MessageMapping("/send-message")
    fun handleMessage (@Payload payload: String) {
        redisService.publishMessage(payload)
    }

    @MessageMapping("/on-connect")
    fun handleConnect(@Headers headers: Message<Any>) {
        val accessor = SimpMessageHeaderAccessor.wrap(headers)

        val authToken = accessor.getFirstNativeHeader("Authorization")
        val username = accessor.getFirstNativeHeader("Username")

        username?.let {
            redisService.subscribeUser(username)
        }  // A bug can be easily created here from front end side, try to avoid it.
    }
}
