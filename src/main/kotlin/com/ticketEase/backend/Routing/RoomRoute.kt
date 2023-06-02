package com.ticketEase.backend.Routing

import com.example.DataClasses.Person.BuyerCity
import com.example.DataClasses.Person.City
import com.ticketEase.backend.DataClasses.TicketCountWithPrice
import com.ticketEase.backend.RoomQuery.CatalogRoom
import com.ticketEase.backend.RoomQuery.CreateEventOrganizer
import com.ticketEase.backend.RoomQuery.PreferenceRoom
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("unused")
fun Route.roomsRoute() {
    val catalogRoom = CatalogRoom()
    val preferencesRoom = PreferenceRoom()
    val createEventOrganizer = CreateEventOrganizer()

    route("/room"){
        post("/catalog") {
            val parameters = call.receive<City>()
            val listEvent = catalogRoom.catalogRoom(parameters.city)
            call.respond(HttpStatusCode.OK,listEvent)
        }
        post("/preferences") {
            val parameters = call.receive<BuyerCity>()
            val listEvent = preferencesRoom.preferencesRoom(parameters.token,parameters.city)
            call.respond(HttpStatusCode.OK,listEvent)
        }
        post("/createTicketListOrganizer"){
            val parameters = call.receive<TicketCountWithPrice>()
            createEventOrganizer.createListTicket(parameters)
            call.respond(HttpStatusCode.OK)
        }
    }
}