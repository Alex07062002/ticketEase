package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Ticket.StatusTicket
import com.example.DataClasses.Ticket.Ticket
import org.jetbrains.exposed.sql.Query

interface TicketTransaction : CRUDOperations<Ticket, Long>{

    suspend fun selectEventByBuyer(buyerId : Long) : Query

    suspend fun selectTicket(eventId : Long, row : Int, column : Int) : Ticket?

    suspend fun updateTicket(ticketId : Long, status : StatusTicket?, buyerId: Long?) : Boolean

    suspend fun createTicket(eventId: Long,row : Int? = null,column: Int? = null, status: StatusTicket = StatusTicket.SALE, price : Double) : Ticket?

    suspend fun selectByEvent(eventId : Long) : List<Ticket>

    suspend fun filterEventByCost(lowPrice: Double, highPrice : Double) : List<Long>
}