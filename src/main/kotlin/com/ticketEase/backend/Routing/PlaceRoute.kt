package com.ticketEase.backend.Routing

import com.example.DataClasses.PlaceDTO
import com.ticketEase.backend.PostgreSQL.Transactions.PlaceTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


@Suppress("unused")
fun Route.placeRoute(){

    val placeService = PlaceTransactionImpl()

    route("/places") {
        post {
            call.respond(placeService.selectAll())
        }
        post("/{id}") {
            val placeIdFromQuery = call.parameters["id"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid id")
            }
            val place = placeService.selectById(placeIdFromQuery.toLong())
            if (place == null) call.respond(
                HttpStatusCode.NotFound,
                "Place isn't find."
            ) else call.respond(HttpStatusCode.OK, place)
        }
        post("/create") {
            val parameters = call.receive<PlaceDTO>()
            if (parameters.id != null) {
                call.respond(call.respond(HttpStatusCode.BadRequest, "Place isn't created."))
            } else {
                val place = placeService.createPlace(parameters)
                if (place == null) call.respond(HttpStatusCode.BadRequest, "Place isn't created") else
                    call.respond(HttpStatusCode.Created, place)
            }
        }

        delete("/{id}") {
            val idFromQuery = call.parameters["id"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid id")
            }
            placeService.delete(idFromQuery.toLong())
            call.respond("Place is deleted.")
        }
        post("/select/{type}") {
            val typeOfPlace = call.parameters["type"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid type")
            }
            call.respond(HttpStatusCode.OK, placeService.selectOneOfTypePlace(typeOfPlace))
        }
        put("/{id}/update") {
            val parameters = call.receive<PlaceDTO>()
            if (parameters.id == null) {
                call.respond(HttpStatusCode.BadRequest, "Place isn't updated")
            } else {
                val place = placeService.updatePlace(parameters)
                if (place == null) call.respond(HttpStatusCode.BadRequest, "Place isn't updated") else
                call.respond(HttpStatusCode.OK,place)
            }
        }
    }
}