package com.techorgx.api.routing

import io.ktor.server.application.Application
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*

fun Application.router() {
    routing {
        get("/") {
            call.respondText("Hello, world!")
        }

        webSocket("/echo") {
            send("Please enter your name")
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                val receivedText = frame.readText()
                if (receivedText.equals("bye", ignoreCase = true)) {
                    close(CloseReason(io.ktor.websocket.CloseReason.Codes.NORMAL, "Client said BYE"))
                } else {
                    send(io.ktor.websocket.Frame.Text("Hi, $receivedText!"))
                }
            }
        }
    }
}
