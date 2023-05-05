package com.example.DataClasses.Ticket

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Person.BuyerTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class Ticket(val id : Long,
                  val eventId : Long,
                  val buyerId : Long? = null,
                  val row : Int? = null,
                  val column : Int? = null,
                  val status : StatusTicket,
                  val price : Double)

object TicketTable : LongIdTable("ticket"){
    val ticketId = long("id").autoIncrement().entityId()
    val eventId = long("event_id").references(EventTable.eventId)
    val buyerId = long("buyer_id").references(BuyerTable.buyerId).nullable()
    val row = integer("row").nullable()
    val column = integer("column").nullable()
    val price = double("price")
    val status = enumeration("status",StatusTicket::class)
}

