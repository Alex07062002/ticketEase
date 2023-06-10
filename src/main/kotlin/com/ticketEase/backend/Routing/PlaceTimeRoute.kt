package com.ticketEase.backend.Routing

import com.example.DataClasses.PlaceId
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeDTO
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeDate
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeId
import com.ticketEase.backend.PostgreSQL.Transactions.PlaceTimeTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
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
            post("/id") {
                val parameters = call.receive<PlaceTimeId>()
                val placeTime = placeTimeService.selectById(parameters.id)
                if (placeTime == null) call.respond(
                    HttpStatusCode.NotFound,
                    "PlaceTime not find"
                ) else call.respond(HttpStatusCode.OK, placeTime)
            }
            delete("/id") {
                val parameters = call.receive<PlaceTimeId>()
                placeTimeService.delete(parameters.id)
                call.respond("PlaceTime is deleted.")
            }
            route("/select") {
                post("/date") {
                    val parameters = call.receive<PlaceTimeDate>()
                    val listTimesPlaces = placeTimeService.selectIdByDate(parameters.date)
                    call.respond(HttpStatusCode.OK,listTimesPlaces)
                }
                post("/id/date") {
                    val parameters = call.receive<PlaceTimeId>()
                    val time = placeTimeService.selectDateById(parameters.id)
                    if (time == null) call.respond(HttpStatusCode.NotFound) else
                        call.respond(HttpStatusCode.OK, time)
                }
                post("/placeId") {
                    val parameters = call.receive<PlaceId>()
                    val listTimesPlaces = placeTimeService.selectByPlace(parameters.id)
                    call.respond(HttpStatusCode.OK,listTimesPlaces)
                }
            }
                put("/update") {
                    val parameters = call.receive<PlaceTimeDTO>()
                    if (parameters.id == null) call.respond(HttpStatusCode.Conflict, "Invalid data") else {
                        val placeTime = placeTimeService.updatePlaceTime(parameters)
                        if (placeTime == null) call.respond(HttpStatusCode.BadRequest, "PlaceTime isn't updated.") else
                            call.respond(HttpStatusCode.OK, placeTime)
                    }
                }
            post("/create") {
                val parameters = call.receive<PlaceTimeDTO>()
                if (parameters.id != null) call.respond(HttpStatusCode.Conflict, "Invalid data") else {
                    val placeTime = placeTimeService.createPlaceTime(parameters)
                    if (placeTime == null) call.respond(HttpStatusCode.BadRequest, "PlaceTime isn't created.") else
                        call.respond(HttpStatusCode.Created, placeTime)
                }
            }
        }
    }