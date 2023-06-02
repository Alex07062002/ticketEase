package com.ticketEase.backend.RoomQuery

import com.example.DataClasses.Ticket.TicketDTO
import com.ticketEase.backend.DataClasses.TicketCountWithPrice
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import com.ticketEase.backend.PostgreSQL.Transactions.TicketTransactionImpl

class CreateEventOrganizer {

    private val ticketService = TicketTransactionImpl()

    suspend fun createListTicket(ticketParams : TicketCountWithPrice) = dbQuery {
        if ((ticketParams.row != null) && (ticketParams.column != null)){
            for (row in 1 .. ticketParams.row) {
                for(column in 1 .. ticketParams.column){
                    ticketService.createTicket(TicketDTO(eventId = ticketParams.eventId,row = row, column = column, price = ticketParams.price))
                }
            }
        }else{
            for (ticket in 1 .. ticketParams.capacity){
                ticketService.createTicket(TicketDTO(eventId = ticketParams.eventId,price = ticketParams.price))
            }
        }
    }
}


