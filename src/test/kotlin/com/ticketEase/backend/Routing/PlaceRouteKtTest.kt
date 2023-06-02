package com.ticketEase.backend.Routing

import com.ticketEase.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class PlaceRouteKtTest {

    @Test
    fun testPostSelectAllPlace() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/places").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostPlaceId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/places/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostPlaceCreate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/places/create").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeletePlaceId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.delete("/places/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeletePlaceId0() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.delete("/places/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostPlaceType() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/places/type").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutPlaceUpdate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.put("/places/update").apply {
            TODO("Please write your test here")
        }
    }
}