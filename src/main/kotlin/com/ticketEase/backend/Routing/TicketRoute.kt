package com.ticketEase.backend.Routing

import com.example.DataClasses.Event.EventId
import com.example.DataClasses.Person.BuyerId
import com.example.DataClasses.Ticket.StatusTicket
import com.example.DataClasses.Ticket.TicketDTO
import com.example.DataClasses.Ticket.TicketId
import com.example.DataClasses.Ticket.TicketWithSeat
import com.ticketEase.backend.PostgreSQL.Transactions.TicketTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

  @Suppress("unused")
  fun Route.ticketRoute(){

      val ticketService = TicketTransactionImpl()

      route("/tickets"){
          post{
              call.respond(HttpStatusCode.OK,ticketService.selectAll())
          }
          post("/id"){
              val parameters = call.receive<TicketId>()
              val ticket = ticketService.selectById(parameters.id)
              if (ticket == null) call.respond(
                  HttpStatusCode.NotFound,
                  "Ticket isn't find."
              ) else call.respond(HttpStatusCode.OK, ticket)
          }
          delete("/id") {
              val parameters = call.receive<TicketId>()
              ticketService.delete(parameters.id)
              call.respond("Ticket is deleted.")
          }
          put("/update"){
              val parameters = call.receive<TicketDTO>()
              if (parameters.id == null) {
                  call.respond(HttpStatusCode.BadRequest, "Ticket isn't updated.")
              } else {
                  val ticket = ticketService.updateTicket(parameters)
                  if (ticket == null) call.respond(HttpStatusCode.BadRequest, "Ticket isn't updated") else
                      call.respond(HttpStatusCode.OK, ticket)
              }
          }
          post("/buyerId"){
              val parameters = call.receive<BuyerId>()
              val ticketList = ticketService.selectEventByBuyer(parameters.id)
              call.respond(HttpStatusCode.OK, ticketList)
          }
          post("/eventId"){
              val parameters = call.receive<EventId>()
              val ticket = ticketService.selectTicket(parameters.id,null,null)
              if (ticket == null) call.respond(HttpStatusCode.NotFound, "Ticket isn't found.") else
                  call.respond(HttpStatusCode.OK,ticket)
          }
          post("/eventId/row/column"){
              val parameters = call.receive<TicketWithSeat>()
              val ticket = ticketService.selectTicket(parameters.id,parameters.row,parameters.column)
              if (ticket == null) call.respond(HttpStatusCode.NotFound, "Ticket isn't found.") else
                  call.respond(HttpStatusCode.OK,ticket)
          }
          post("/eventId/list"){
              val parameters = call.receive<EventId>()
              val ticketList = ticketService.selectByEvent(parameters.id, StatusTicket.SOLD)
                  call.respond(HttpStatusCode.OK,ticketList)
          }
          post("/eventId/soldTicket"){
              val parameters = call.receive<EventId>()
              val countSoldTicket = ticketService.countSoldTicket(parameters.id, StatusTicket.SOLD)
              call.respond(HttpStatusCode.OK,countSoldTicket)
          }
          /**
           * Filtration isn't realization
           */
         /* post("/{eventId}/{status}/search"){
              val eventId = call.parameters["eventId"] ?: kotlin.run{
                  throw NotFoundException("Not found ticket with this event id")
              }
              val statusType = call.parameters["status"] ?: kotlin.run{
              throw NotFoundException("Not found ticket with this event id")
          }
              val ticketList = ticketService.selectByEvent(eventId.toLong(),StatusTicket.valueOf(statusType))
              call.respond(HttpStatusCode.OK,ticketList)
          }
          }
          post("/filter/{low}/{high}"){
              val lowCost = call.parameters["low"] ?: kotlin.run{
                  throw NotFoundException("Not found low cost")
              }
              val highCost = call.parameters["high"] ?: kotlin.run{
                  throw NotFoundException("Not found high cost")
              }
              val eventIdList = ticketService.filterEventByCost(lowCost.toDouble(),highCost.toDouble())
              call.respond(HttpStatusCode.OK,eventIdList)
              }*/
          }
      }
