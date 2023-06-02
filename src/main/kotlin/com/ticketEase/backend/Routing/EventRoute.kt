package com.ticketEase.backend.Routing

import com.example.DataClasses.Event.*
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeId
import com.ticketEase.backend.PostgreSQL.Transactions.EventTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("unused")
fun Route.eventRoute(){

    val eventService = EventTransactionImpl()

    route("/events"){
        post{
            call.respond(HttpStatusCode.OK,eventService.selectAll())
        }
        post("/id"){
            val parameters = call.receive<EventId>()
            val event = eventService.selectById(parameters.id)
            if (event == null) call.respond(
                HttpStatusCode.NotFound,
                "Event isn't find."
            ) else call.respond(HttpStatusCode.OK, event)
        }
        delete("/id") {
            val parameters = call.receive<EventId>()
            eventService.delete(parameters.id)
            call.respond("Event is deleted.")
        }
        post("/create"){
            val parameters = call.receive<EventDTO>()
            if (parameters.id != null) {
                call.respond(HttpStatusCode.BadRequest, "Event isn't created.")
            } else {
                val event = eventService.createEvent(parameters)
                if (event == null) call.respond(HttpStatusCode.BadRequest, "Event isn't created") else
                    call.respond(HttpStatusCode.Created, event)
            }
        }
       /* post("/preferences"){
            val parameters = call.receive<GenreTypeId>()
            val eventList = eventService.selectEventByGenreOrType(
                parameters.genre,
                parameters.type)
            call.respond(HttpStatusCode.OK,eventList)
        }*/
        post("/placeTimeId"){
            val parameters = call.receive<PlaceTimeId>()
            val eventList = eventService.selectEventByPlaceTime(parameters.id)
            if (eventList.isNotEmpty()) call.respond(HttpStatusCode.OK,eventList) else
                call.respond(HttpStatusCode.BadRequest, "PlaceTime isn't found")
        }
    }
}