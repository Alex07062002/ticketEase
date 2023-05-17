package com.ticketEase.backend.Routing

import com.example.DataClasses.Ticket.StatusTicket
import com.example.DataClasses.Ticket.TicketDTO
import com.ticketEase.backend.PostgreSQL.Transactions.TicketTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
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
          post("/{id}"){
              val ticketId = call.parameters["id"] ?: kotlin.run{
                  throw NotFoundException("Not found ticket with this id")
              }
              val ticket = ticketService.selectById(ticketId.toLong())
              if (ticket == null) call.respond(
                  HttpStatusCode.NotFound,
                  "Ticket isn't find."
              ) else call.respond(HttpStatusCode.OK, ticket)
          }
          delete("/{id}") {
              val ticketId = call.parameters["id"] ?: kotlin.run{
                  throw NotFoundException("Not found ticket with this id")
              }
              ticketService.delete(ticketId.toLong())
              call.respond("Ticket is deleted.")
          }
          post("/create"){
              val parameters = call.receive<TicketDTO>()
              if (parameters.id != null) {
                  call.respond(HttpStatusCode.BadRequest, "Ticket isn't created.")
              } else {
                  val ticket = ticketService.createTicket(parameters)
                  if (ticket == null) call.respond(HttpStatusCode.BadRequest, "Ticket isn't created") else
                      call.respond(HttpStatusCode.Created, ticket)
              }
          }
          put("/{id}/update"){
              val parameters = call.receive<TicketDTO>()
              if (parameters.id == null) {
                  call.respond(HttpStatusCode.BadRequest, "Ticket isn't updated.")
              } else {
                  val ticket = ticketService.updateTicket(parameters)
                  if (ticket == null) call.respond(HttpStatusCode.BadRequest, "Ticket isn't updated") else
                      call.respond(HttpStatusCode.OK, ticket)
              }
          }
          post("/{buyerId}"){
              val buyerId = call.parameters["buyerId"] ?: kotlin.run{
                  throw NotFoundException("Not found ticket with this buyer id")
              }
              val ticketList = ticketService.selectEventByBuyer(buyerId.toLong())
              call.respond(HttpStatusCode.OK, ticketList)
          }
          post("/{eventId}"){
              val eventId = call.parameters["eventId"] ?: kotlin.run{
                  throw NotFoundException("Not found ticket with this event id")
              }
              val ticket = ticketService.selectTicket(eventId.toLong(),null,null)
              if (ticket == null) call.respond(HttpStatusCode.NotFound, "Ticket isn't found.") else
                  call.respond(HttpStatusCode.OK,ticket)
          }
          post("/{eventId}/{row}/{column}"){
              val eventId = call.parameters["eventId"] ?: kotlin.run{
                  throw NotFoundException("Not found ticket with this event id")
              }
              val row = call.parameters["row"] ?: kotlin.run{
                  throw NotFoundException("Not found ticket with row")
              }
              val column = call.parameters["column"] ?: kotlin.run{
                  throw NotFoundException("Not found ticket with this column")
              }
              val ticket = ticketService.selectTicket(eventId.toLong(),row.toInt(),column.toInt())
              if (ticket == null) call.respond(HttpStatusCode.NotFound, "Ticket isn't found.") else
                  call.respond(HttpStatusCode.OK,ticket)
          }
          post("/{eventId}/{status}/search"){
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
          }
      }
