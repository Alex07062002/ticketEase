package com.example.DataClasses

import com.example.DataClasses.Person.BuyerTable
import com.example.DataClasses.Ticket.TicketTable

import com.ticketEase.backend.DataClasses.DateSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

@Serializable
data class CartDTO(val buyerId: Long,
                   val ticketId : Long,
                   @Serializable(with = DateSerializer::class)
                   val orderDate : Instant? = null)

object CartTable : Table("cart"){
    val buyerId = long("buyer_id").references(BuyerTable.id)
    val ticketId = long("ticket_id").references(TicketTable.id)
    val orderDate = timestamp("orderDate").nullable()
    override val primaryKey = PrimaryKey(buyerId, ticketId, name = "CartPK")

}

