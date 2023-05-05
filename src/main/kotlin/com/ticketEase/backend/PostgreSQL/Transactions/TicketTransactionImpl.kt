package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Ticket.StatusTicket
import com.example.DataClasses.Ticket.Ticket
import com.example.DataClasses.Ticket.TicketTable
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory


class TicketTransactionImpl : TicketTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val ticket = TicketTable

    private fun ticketDBToTicketEntity(rs : ResultRow) = Ticket(
        id = rs[ticket.ticketId].value,
        eventId = rs[ticket.eventId],
        buyerId = rs[ticket.buyerId],
        row = rs[ticket.row],
        column = rs[ticket.column],
        status = rs[ticket.status],
        price = rs[ticket.price]
    )

    override suspend fun selectEventByBuyer(buyerId: Long): Query = dbQuery{
        logger.info("Ticket select event by buyer transaction is started")
        ticket.slice(ticket.eventId).select{ticket.buyerId eq buyerId}
    }

    override suspend fun selectTicket(eventId: Long, row: Int, column: Int): Ticket? = dbQuery{
        logger.info("Ticket select transaction is started.")
        ticket.select{ticket.eventId eq eventId; ticket.row eq row; ticket.column eq column}
            .map(::ticketDBToTicketEntity).singleOrNull()
    }

    override suspend fun updateTicket(ticketId: Long, status: StatusTicket?, buyerId: Long?): Boolean = dbQuery {
        logger.info("Ticket update transaction is started.")
        val updateTicket = selectById(ticketId)
        if (updateTicket != null){
        ticket.update ({ticket.ticketId eq ticketId}){
                it[this.status] = status ?: updateTicket.status
                it[this.buyerId] = buyerId ?: updateTicket.buyerId
            }
            logger.info("Ticket update transaction is ended.")
            return@dbQuery true
        }else{
            logger.warn("Ticket isn't find")
            return@dbQuery false
        }
    }

    override suspend fun createTicket(eventId: Long, row : Int?, column: Int?, status: StatusTicket, price: Double): Ticket? = dbQuery{
        val insertStatement = ticket.insert {
            it[this.eventId] = eventId
            it[this.status] = status
            it[this.row] = row
            it[this.column] = column
            it[this.price] = price
        }
        logger.info("Ticket create transaction is started.")
        insertStatement.resultedValues?.singleOrNull()?.let(::ticketDBToTicketEntity)
    }

    override suspend fun selectByEvent(eventId: Long): List<Ticket>  = dbQuery{
        logger.info("Ticket select by event transaction is started.")
        ticket.select(ticket.eventId eq eventId).map(::ticketDBToTicketEntity)
    }

    override suspend fun filterEventByCost(lowPrice: Double, highPrice: Double): List<Long>  = dbQuery{
        logger.info("Ticket filter by price transaction is started.")
        ticket.slice(ticket.eventId).select{ticket.price greaterEq lowPrice;ticket.price lessEq highPrice}
            .map{it[ticket.eventId]}
    }

    override suspend fun selectAll(): List<Ticket>  = dbQuery{
        logger.info("Ticket select all transaction is started.")
        ticket.selectAll().map(::ticketDBToTicketEntity)
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        logger.info("Ticket delete transaction is started.")
        ticket.deleteWhere {ticket.ticketId eq id}
    } > 0

    override suspend fun selectById(id: Long): Ticket? = dbQuery {
        logger.info("Ticket select by id transaction is started.")
        ticket.select(ticket.ticketId eq id).map(::ticketDBToTicketEntity).singleOrNull()
    }
}