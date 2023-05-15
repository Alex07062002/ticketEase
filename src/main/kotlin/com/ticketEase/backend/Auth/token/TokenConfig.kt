package com.ticketEase.backend.Auth.token

data class TokenConfig(
    val issuer : String,
    val audience : String,
    val expiresIn : Long,
    val secret : String
)
