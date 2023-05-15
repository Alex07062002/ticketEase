package com.ticketEase.plugins

import com.ticketEase.backend.Auth.token.TokenConfig
import com.ticketEase.backend.Routing.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*

fun Application.configureRouting(tokenConfig : TokenConfig) {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        placeRoute()
        cartRoute()
        eventRoute()
        favoriteRoute()
        placeTimeRoute()
        ticketRoute()
        buyerRoute(tokenConfig)
        organizerRoute(tokenConfig)
    }
}
