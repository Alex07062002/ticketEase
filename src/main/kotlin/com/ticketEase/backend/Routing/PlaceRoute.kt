package com.ticketEase.backend.Routing

import com.example.DataClasses.Person.Cities
import com.example.DataClasses.PlaceDTO
import com.example.DataClasses.PlaceId
import com.example.DataClasses.PlaceType
import com.ticketEase.backend.PostgreSQL.Transactions.PlaceTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
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
        post("/id") {
            val parameters = call.receive<PlaceId>()
            val place = placeService.selectById(parameters.id)
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

        delete("/id") {
            val parameters = call.receive<PlaceId>()
            placeService.delete(parameters.id)
            call.respond("Place is deleted.")
        }
        post("/type") {
            val parameters = call.receive<PlaceType>()
            call.respond(HttpStatusCode.OK, placeService.selectOneOfTypePlace(parameters.type, Cities.valueOf(parameters.city)))
        }
        put("/update") {
            val parameters = call.receive<PlaceDTO>()
            if (parameters.id == null) {
                call.respond(HttpStatusCode.BadRequest, "Place isn't updated")
            } else {
                val place = placeService.updatePlace(parameters)
                if (place == null) call.respond(HttpStatusCode.BadRequest, "Place isn't updated") else
                call.respond(HttpStatusCode.OK,place)
            }
        }
        post("/capacity"){
            val parameters = call.receive<PlaceId>()
            val capacity = placeService.capacity(parameters.id)
            call.respond(HttpStatusCode.OK,capacity)
        }
    }
}