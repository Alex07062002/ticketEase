package com.ticketEase.backend.Routing

import com.ticketEase.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class PlaceTimeRouteKtTest {

    @Test
    fun testPostSelectAllPlacetimes() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/placeTimes").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostPlacetimeId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/placeTimes/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeletePlacetimeId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.delete("/placeTimes/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostPlacetimeSelectDate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/placeTimes/select/date").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostPlacetimeSelectIdDate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/placeTimes/select/id/date").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostPlacetimeSelectByPlaceId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/placeTimes/select/placeId").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutPlacetimeUpdate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.put("/placeTimes/update").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostPlacetimeCreate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/placeTimes/create").apply {
            TODO("Please write your test here")
        }
    }
}