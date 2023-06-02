package com.ticketEase.backend.Routing

import com.ticketEase.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class EventRouteKtTest {

    @Test
    fun testPostSelectAllEvent() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/events").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostEventId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/events/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteEventId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.delete("/events/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostEventCreate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/events/create").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostEventsPlacetimeId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/events/placeTimeId").apply {
            TODO("Please write your test here")
        }
    }
}