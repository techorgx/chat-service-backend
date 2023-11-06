package com.techorgx.api.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.websocket.*
import java.time.Duration

fun Application.config() {
    install(WebSockets) {
        pingPeriod = Duration.ofSeconds(15)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }
}
