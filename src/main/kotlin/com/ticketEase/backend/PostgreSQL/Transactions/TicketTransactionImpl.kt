package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Ticket.StatusTicket
import com.example.DataClasses.Ticket.TicketDTO
import com.example.DataClasses.Ticket.TicketTable
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory


class TicketTransactionImpl : TicketTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val ticket = TicketTable

    private fun ticketDBToTicketEntity(rs : ResultRow) = TicketDTO(
        id = rs[ticket.id].value,
        eventId = rs[ticket.eventId],
        buyerId = rs[ticket.buyerId],
        row = rs[ticket.row],
        column = rs[ticket.column],
        status = rs[ticket.status],
        price = rs[ticket.price]
    )

    override suspend fun selectEventByBuyer(buyerId: Long): Query = dbQuery{
        logger.info("Ticket select event by buyer id $buyerId transaction is started")
        ticket.slice(ticket.eventId).select{ticket.buyerId eq buyerId}
    }

    override suspend fun selectTicket(eventId: Long, row: Int?, column: Int?): TicketDTO? = dbQuery{
        logger.info("Ticket select by event $eventId transaction is started.")
        ticket.select{ticket.eventId eq eventId; ticket.row eq row; ticket.column eq column}
            .map(::ticketDBToTicketEntity).singleOrNull()
    }

    override suspend fun updateTicket(ticketDTO: TicketDTO): TicketDTO? {
        dbQuery {
                ticket.update({ ticket.id eq ticketDTO.id }) {
                    it[this.status] = ticketDTO.status
                    it[this.buyerId] = ticketDTO.buyerId
                }
        }
        return ticketDTO.id?.let { selectById(it) }
    }


    override suspend fun createTicket(ticketDTO: TicketDTO): TicketDTO? = dbQuery{
        logger.info("Ticket create transaction is started.")
        val insertStatement = ticket.insert {
            it[this.eventId] = ticketDTO.eventId
            it[this.status] = ticketDTO.status
            it[this.row] = ticketDTO.row
            it[this.column] = ticketDTO.column
            it[this.price] = ticketDTO.price
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::ticketDBToTicketEntity)
    }

    override suspend fun selectByEvent(eventId: Long,status: StatusTicket): List<TicketDTO>  = dbQuery{
        logger.info("Ticket select tickets by event $eventId transaction is started.")
        ticket.select{ticket.eventId eq eventId;ticket.status eq status}.map(::ticketDBToTicketEntity)
    }

    override suspend fun filterEventByCost(lowPrice: Double, highPrice: Double): List<Long>  = dbQuery{
        logger.info("Ticket filter by price transaction is started.")
        ticket.slice(ticket.eventId).select{ticket.price greaterEq lowPrice;ticket.price lessEq highPrice}
            .map{it[ticket.eventId]}
    }

    override suspend fun selectAll(): List<TicketDTO>  = dbQuery{
        logger.info("Ticket select all transaction is started.")
        ticket.selectAll().map(::ticketDBToTicketEntity)
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        logger.info("Ticket $id delete transaction is started.")
        ticket.deleteWhere {ticket.id eq id}
    } > 0

    override suspend fun selectById(id: Long): TicketDTO? = dbQuery {
        logger.info("Ticket $id select by id transaction is started.")
        ticket.select(ticket.id eq id).map(::ticketDBToTicketEntity).singleOrNull()
    }
}