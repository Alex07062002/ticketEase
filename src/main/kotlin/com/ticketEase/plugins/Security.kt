package com.ticketEase.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.ticketEase.backend.Auth.token.TokenConfig
import io.ktor.server.application.*

fun Application.configureSecurity(config : TokenConfig) {
    authentication {
        jwt {
//            realm = this@configureSecurity.environment.config.property("jwt.realm").getString()
            realm = realm // TODO Fix this
            verifier(
                JWT
                    .require(Algorithm.HMAC256(config.secret))
                    .withAudience(config.audience)
                    .withIssuer(config.issuer)
                    .build()
            )
                validate { credential ->
                    if (credential.payload.audience.contains(config.audience)) JWTPrincipal(credential.payload) else null
            }
        }
    }
}
