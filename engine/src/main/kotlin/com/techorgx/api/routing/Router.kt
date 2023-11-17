package com.techorgx.api.routing

import org.springframework.messaging.Message
import org.springframework.messaging.handler.annotation.Headers
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.stereotype.Controller


@Controller
class Router {
    @MessageMapping("/send-message")
    fun handleMessage (@Payload message: String) {
        println(message)
    }

    @MessageMapping("/on-connect")
    fun handleConnect(@Headers headers: Message<Any>) {
        val accessor = SimpMessageHeaderAccessor.wrap(headers)

        val authorizationHeader = accessor.getFirstNativeHeader("Authorization")
        val usernameHeader = accessor.getFirstNativeHeader("Username")

        println("Authorization Header: $authorizationHeader")
        println("Username Header: $usernameHeader")
    }
}
