package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Ticket.StatusTicket
import com.example.DataClasses.Ticket.TicketDTO

interface TicketTransaction : CRUDOperations<TicketDTO, Long>{

    suspend fun selectEventByBuyer(buyerId : Long) : List<Long>

    suspend fun selectTicket(eventId : Long, row : Int?, column : Int?) : TicketDTO?

    suspend fun updateTicket(ticketDTO: TicketDTO) : TicketDTO?

    suspend fun createTicket(ticketDTO: TicketDTO)

    suspend fun selectByEvent(eventId : Long, status: StatusTicket) : List<TicketDTO>

    suspend fun filterEventByCost(lowPrice: Double, highPrice : Double) : List<Long>

    suspend fun delete(id : Long) : Boolean

    suspend fun countSoldTicket(eventId: Long, status: StatusTicket = StatusTicket.SOLD) : Long
}