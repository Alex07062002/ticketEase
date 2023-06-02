package com.ticketEase.backend.Routing

import com.ticketEase.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class RoomRouteKtTest {

    @Test
    fun testPostRoomCreateTicketListOrganizer() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/room/createTicketListOrganizer").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostRoomPreferences() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/room/preferences").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostRoomCatalog() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/room/catalog").apply {
            TODO("Please write your test here")
        }
    }
}