package com.example.DataClasses.Ticket

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Person.BuyerTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant


data class TicketDTO(val id : Long? = null,
                     val eventId : Long,
                     val buyerId : Long? = null,
                     val row : Int? = null,
                     val column : Int? = null,
                     val status : StatusTicket = StatusTicket.SALE,
                     val price : Double,
                     val orderDate : Instant? = null)

@Serializable
data class TicketId(val id : Long)

@Serializable
data class TicketWithSeat(val id : Long, val row : Int, val column : Int)

object TicketTable : LongIdTable("ticket"){
    val eventId = long("event_id").references(EventTable.id)
    val buyerId = long("buyer_id").references(BuyerTable.id).nullable()
    val row = integer("row").nullable()
    val column = integer("column").nullable()
    val price = double("price")
    val status = varchar("status",30)
    val orderDate = timestamp("orderDate").nullable()
}

