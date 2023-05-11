package com.ticketEase.backend.Routing

import com.example.DataClasses.Event.EventDTO
import com.example.DataClasses.Event.GenreList
import com.example.DataClasses.Event.TypeList
import com.example.DataClasses.Person.Cities
import com.ticketEase.backend.PostgreSQL.Transactions.EventTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
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
        post("/{id}"){
            val eventId = call.parameters["id"] ?: kotlin.run{
                throw NotFoundException("Not found event with this id")
            }
            val event = eventService.selectById(eventId.toLong())
            if (event == null) call.respond(
                HttpStatusCode.NotFound,
                "Event isn't find."
            ) else call.respond(HttpStatusCode.OK, event)
        }
        delete("/{id}") {
            val eventId = call.parameters["id"] ?: kotlin.run{
                throw NotFoundException("Not found event with this id")
            }
            eventService.delete(eventId.toLong())
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
        post("/{genre}/{type}"){
            val genre = call.parameters["genre"]
                if (GenreList.values().any {it.toString() == genre}) genre ?: kotlin.run{
                throw NotFoundException("Not found genre")
            }
            val type = call.parameters["type"]
                if (TypeList.values().any{it.toString() == type}) type ?: kotlin.run{
                throw NotFoundException("Not found type")
            }
            val eventList = eventService.selectEventByGenreOrType(
                GenreList.valueOf(type.orEmpty()),
                TypeList.valueOf(type.orEmpty()))
            call.respond(HttpStatusCode.OK,eventList)
        }
        post("/{buyerId}"){
            val buyerId = call.parameters["buyerId"] ?: kotlin.run{
                throw NotFoundException("Not found buyer id")
            }
            val eventList = eventService.selectGenreForPreferences(buyerId.toLong())
            if (eventList.isNotEmpty()) call.respond(HttpStatusCode.OK,eventList) else
                call.respond(HttpStatusCode.BadRequest, "Buyer isn't found")
        }
        post("/{placeTimeId}"){
            val placeTimeId = call.parameters["placeTimeId"] ?: kotlin.run{
                throw NotFoundException("Not found placeTime id")
            }
            val eventList = eventService.selectEventByPlaceTime(placeTimeId.toLong())
            if (eventList.isNotEmpty()) call.respond(HttpStatusCode.OK,eventList) else
                call.respond(HttpStatusCode.BadRequest, "PlaceTime isn't found")
        }
        post("/{city}"){
            val city = call.parameters["city"]
            if (Cities.values().any{it.toString() == city}) city ?: kotlin.run{
                throw NotFoundException("Not found city")
            }
            val eventList = eventService.selectEventByCity(Cities.valueOf(city.orEmpty()))
            call.respond(HttpStatusCode.OK,eventList)
        }
    }
}