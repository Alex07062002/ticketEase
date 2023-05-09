package com.ticketEase.plugins

import com.ticketEase.backend.Routing.placeRoute
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        placeRoute()
    }
}
