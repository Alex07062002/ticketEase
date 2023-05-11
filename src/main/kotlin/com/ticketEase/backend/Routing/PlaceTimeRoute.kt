package com.ticketEase.backend.Routing

import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeDTO
import com.ticketEase.backend.PostgreSQL.Transactions.` Adapters`.DateAdapter
import com.ticketEase.backend.PostgreSQL.Transactions.PlaceTimeTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("unused")
fun Route.placeTimeRoute(){

    val placeTimeService = PlaceTimeTransactionImpl()

        route("/placeTimes") {
            post {
                call.respond(HttpStatusCode.OK, placeTimeService.selectAll())
            }
            post("/{id}") { // TODO Exception
                val placeTimeIdFromQuery = call.parameters["id"] ?: kotlin.run {
                    throw NotFoundException("Please provide a valid id")
                }
                val placeTime = placeTimeService.selectById(placeTimeIdFromQuery.toLong())
                if (placeTime == null) call.respond(
                    HttpStatusCode.NotFound,
                    "PlaceTime not find"
                ) else call.respond(HttpStatusCode.OK, placeTime)
            }
            delete("/{id}") {
                val bookIdFromQuery = call.parameters["id"] ?: kotlin.run {
                    throw NotFoundException("Please provide a valid id")
                }
                placeTimeService.delete(bookIdFromQuery.toLong())
                call.respond("PlaceTime is deleted.")
            }
            route("/select") {
                post("/{date}") {//TODO Fix method {date}
                    val dateIdFromQuery = call.parameters["date"] ?: kotlin.run {
                        throw NotFoundException("Please provide a valid date")
                    }
                    placeTimeService.selectIdByDate(DateAdapter.stringToInstant(dateIdFromQuery))
                }
                post("/{id}") {
                    val idFromQuery = call.parameters["id"] ?: kotlin.run {
                        throw NotFoundException("Please provide a valid id")
                    }
                    placeTimeService.selectDateById(idFromQuery.toLong())
                }
                post("/{placeId}") {
                    val placeIdFromQuery = call.parameters["placeId"] ?: kotlin.run {
                        throw NotFoundException("Please provide a valid id")
                    }
                    placeTimeService.selectByPlace(placeIdFromQuery.toLong())
                }
                put("/update") {
                    val parameters = call.receive<PlaceTimeDTO>()
                    if (parameters.id == null) call.respond(HttpStatusCode.BadRequest, "Invalid data") else {
                        val placeTime = placeTimeService.updatePlaceTime(parameters)
                        if (placeTime == null) call.respond(HttpStatusCode.BadRequest, "PlaceTime isn't updated.") else
                            call.respond(HttpStatusCode.OK, placeTime)
                    }
                }
            }
            post("/create") {
                val parameters = call.receive<PlaceTimeDTO>()
                if (parameters.id != null) call.respond(HttpStatusCode.BadRequest, "Invalid data") else {
                    val placeTime = placeTimeService.createPlaceTime(parameters)
                    if (placeTime == null) call.respond(HttpStatusCode.BadRequest, "PlaceTime isn't created.") else
                        call.respond(HttpStatusCode.Created, placeTime)
                }
            }
        }
    }