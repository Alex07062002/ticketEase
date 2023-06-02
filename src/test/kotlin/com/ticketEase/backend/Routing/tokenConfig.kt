package com.ticketEase.backend.Routing

import com.ticketEase.backend.Auth.token.TokenConfig
import com.ticketEase.config
import com.ticketEase.plugins.JWT_SECRET

    val TOKEN_CONFIG = TokenConfig(
        issuer = config.property("jwt.issuer").getString(),
        audience = config.property("jwt.audience").getString(),
        expiresIn = 365L * 1000L * 60L * 60L * 24L,
        secret = JWT_SECRET)
