package com.ticketEase.backend.Auth.token

interface TokenService {
    fun generate(
        config : TokenConfig,
        vararg claim: TokenClaim
    ) : String
}