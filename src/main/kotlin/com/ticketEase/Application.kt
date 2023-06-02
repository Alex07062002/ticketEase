package com.ticketEase

import com.ticketEase.backend.Auth.token.TokenConfig
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.ticketEase.plugins.*
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*

val config = HoconApplicationConfig(ConfigFactory.load())

// total 1971 rows code
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {

    DataBaseFactory.init()
    val tokenConfig = TokenConfig(
        issuer = config.property("jwt.issuer").getString(),
        audience = config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = JWT_SECRET

    )
    configureSecurity(tokenConfig, config)
    configureHTTP()
    configureMonitoring()
    configureSerialization()
    configureRouting(tokenConfig)
}
