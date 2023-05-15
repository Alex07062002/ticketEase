package com.ticketEase

import com.ticketEase.backend.Auth.token.TokenConfig
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.ticketEase.plugins.*

const val issuer = "123" //TODO delete this
const val audience = "234" // TODO delete this

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DataBaseFactory.init()
    val tokenConfig = TokenConfig(
     //   issuer = environment.config.property("jwt.issuer").getString(),
      //  audience = environment.config.property("jwt.audience").getString(),
        issuer = issuer,// TODO Fix this
        audience = audience, // TODO Fix this
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        //secret = System.getenv("JWT_SECRET")
        secret = "JWT_SECRET" // TODO Fix this

    )
    configureSecurity(tokenConfig)
    //configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting(tokenConfig)
}
