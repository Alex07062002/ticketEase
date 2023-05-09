package com.ticketEase.backend.Routing

import com.example.DataClasses.NewPlace
import com.ticketEase.backend.PostgreSQL.Transactions.PlaceTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*


@Suppress("unused")
fun Route.placeRoute(){

    val placeService = PlaceTransactionImpl()


    route("/places") {
        post() {
            call.respond(placeService.selectAll())
        }
        post("/{id}") {
            val placeIdFromQuery = call.parameters["id"] ?: kotlin.run {
                throw Exception("Please provide a valid id") // TODO open Exception
            }
            val place = placeService.selectById(placeIdFromQuery.toLong())
            if (place == null) call.respond(
                HttpStatusCode.NotFound,
                "Place not find"
            ) else call.respond(HttpStatusCode.OK, place)
        }
        post("/create") { //TODO Fix method through receiveParameters
            val place = call.receive<NewPlace>()
            val placeEnd = placeService.createPlace(place)
            if (placeEnd == null) call.respond(HttpStatusCode.BadRequest, "Place not created") else call.respond(
                HttpStatusCode.Created,
                placeEnd
            )
        }

        delete("/{id}") {
            val IdFromQuery = call.parameters["id"] ?: kotlin.run {
                throw Exception("Please provide a valid id")
            }
            placeService.delete(IdFromQuery.toLong())
            call.respond("Place is deleted.")
        }
        post("/select/{type}") {
            val typeOfPlace = call.parameters["type"] ?: kotlin.run {
                throw Exception("Please provide a valid id")
            }
            call.respond(HttpStatusCode.OK, placeService.selectOneOfTypePlace(typeOfPlace))
        }
        put("/{id}/update") { // TODO Fix method through receiveParameters
            //TODO Don't work
            val id = call.parameters.getOrFail<Int>("id").toLong()
            val formParameters = call.receiveParameters()
            val name = formParameters.getOrFail("name")
            val capacity = formParameters.getOrFail("capacity")
            val numRow = formParameters.getOrFail("numRow")
            val numColumn = formParameters.getOrFail("numColumn")

            call.respond(
                HttpStatusCode.OK,
                placeService.updatePlace(
                    id,
                    name,
                    capacity.toLongOrNull(),
                    numRow.toIntOrNull(),
                    numColumn.toIntOrNull()))
        }
    }
}