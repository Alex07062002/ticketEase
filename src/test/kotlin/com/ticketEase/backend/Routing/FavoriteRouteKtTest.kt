package com.ticketEase.backend.Routing

import com.ticketEase.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class FavoriteRouteKtTest {

    @Test
    fun testPostSelectAllFavorite() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/favorites").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostFavoriteBuyeridEventid() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/favorites/buyerId/eventId").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteFavoriteBuyeridEventid() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.delete("/favorites/buyerId/eventId").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostFavoriteCreate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/favorites/create").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutFavoriteUpdate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.put("/favorites/update").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostFavoriteBuyerId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/favorites/buyerId").apply {
            TODO("Please write your test here")
        }
    }
}