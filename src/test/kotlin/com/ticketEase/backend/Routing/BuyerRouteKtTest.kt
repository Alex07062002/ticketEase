package com.ticketEase.backend.Routing

import com.ticketEase.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class BuyerRouteKtTest {

    @Test
    fun testPostSelectAllBuyer() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/buyers").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteBuyerDelete() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.delete("/buyers/delete").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostBuyerCreate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/buyers/create").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostBuyerToken() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/buyers/token").apply {
            TODO("Please write your test here")
        }
    }


    @Test
    fun testPutBuyerUpdate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.put("/buyers/update").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostBuyerSignIn() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/buyers/signIn").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostBuyerLoginCheck() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/buyers/login/check").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostBuyerUpdateCity() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/buyers/updateCity").apply {
            TODO("Please write your test here")
        }
    }
}