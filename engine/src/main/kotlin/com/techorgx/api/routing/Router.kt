package com.techorgx.api.routing

import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.stereotype.Controller

@Controller
class Router {
    @MessageMapping("/send-message")
    fun message (message: String) {
        println(message)
    }
}
