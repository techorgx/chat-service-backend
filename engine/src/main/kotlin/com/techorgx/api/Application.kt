package com.techorgx.api

import com.techorgx.api.config.config
import com.techorgx.api.routing.router
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    config()
    router()
}
