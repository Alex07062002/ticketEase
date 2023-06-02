package com.ticketEase.backend.Routing

import com.ticketEase.plugins.configureRouting
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class OrganizerRouteKtTest {

    @Test
    fun testPostSelectAllOrganizer() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/organizers").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testDeleteOrganizerDelete() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.delete("/organizers/delete").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostOrganizerCreate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/organizers/create").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostOrganizerToken() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/organizers/token").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutOrganizerUpdate() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.put("/organizers/update").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPutOrganizerSignin() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.put("/organizers/signIn").apply {
            TODO("Please write your test here")
        }
    }

    @Test
    fun testPostOrganizerUpdatecity() = testApplication {
        application {
            configureRouting(TOKEN_CONFIG)
        }
        client.post("/organizers/updateCity").apply {
            TODO("Please write your test here")
        }
    }
}