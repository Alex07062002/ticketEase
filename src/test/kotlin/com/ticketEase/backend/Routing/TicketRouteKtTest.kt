package com.ticketEase.backend.Routing

import com.ticketEase.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class TicketRouteKtTest {

    @Test
    fun testPostSelectAllTickets() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/tickets").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostTicketId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/tickets/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteTicketId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.delete("/tickets/id").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutTicketUpdate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.put("/tickets/update").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostTicketByBuyerId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/tickets/buyerId").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostTicketsByEventId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/tickets/eventId").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostTicketListByEventId() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/tickets/eventId/list").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostTicketByEventIdRowColumn() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/tickets/eventId/row/column").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostTicketsEventIdSoldTicket() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/tickets/eventId/soldTicket").apply {
            TODO("Please write your test here")
        }
    }
}