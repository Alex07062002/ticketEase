package com.ticketEase.backend.Routing

import com.ticketEase.backend.PostgreSQL.Transactions.TicketTransactionImpl
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*

  /**  override suspend fun selectEventByBuyer(buyerId: Long): Query = DataBaseFactory.dbQuery {
        logger.info("Ticket select event by buyer id $buyerId transaction is started")
        ticket.slice(ticket.eventId).select { ticket.buyerId eq buyerId }
    }

    override suspend fun selectTicket(eventId: Long, row: Int, column: Int): Ticket? = DataBaseFactory.dbQuery {
        logger.info("Ticket select by event $eventId transaction is started.")
        ticket.select { ticket.eventId eq eventId; ticket.row eq row; ticket.column eq column }
            .map(::ticketDBToTicketEntity).singleOrNull()
    }

    override suspend fun updateTicket(ticketId: Long, status: StatusTicket?, buyerId: Long?): Boolean =
        DataBaseFactory.dbQuery {
            logger.info("Ticket $ticketId update transaction is started.")
            val updateTicket = selectById(ticketId)
            if (updateTicket != null) {
                ticket.update({ ticket.id eq ticketId }) {
                    it[this.status] = status ?: updateTicket.status
                    it[this.buyerId] = buyerId ?: updateTicket.buyerId
                }
                logger.info("Ticket $ticketId update transaction is ended.")
                return@dbQuery true
            } else {
                logger.warn("Ticket $ticketId isn't find")
                return@dbQuery false
            }
        }

    override suspend fun createTicket(eventId: Long, row : Int?, column: Int?, status: StatusTicket, price: Double): Ticket? =
        DataBaseFactory.dbQuery {
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

    override suspend fun selectByEvent(eventId: Long,status: StatusTicket): List<Ticket>  = DataBaseFactory.dbQuery {
        logger.info("Ticket select tickets by event $eventId transaction is started.")
        ticket.select(ticket.eventId eq eventId).map(::ticketDBToTicketEntity)
    }

    override suspend fun filterEventByCost(lowPrice: Double, highPrice: Double): List<Long>  = DataBaseFactory.dbQuery {
        logger.info("Ticket filter by price transaction is started.")
        ticket.slice(ticket.eventId).select { ticket.price greaterEq lowPrice;ticket.price lessEq highPrice }
            .map { it[ticket.eventId] }
    }

    override suspend fun selectAll(): List<Ticket>  = DataBaseFactory.dbQuery {
        logger.info("Ticket select all transaction is started.")
        ticket.selectAll().map(::ticketDBToTicketEntity)
    }

    override suspend fun delete(id: Long): Boolean = DataBaseFactory.dbQuery {
        logger.info("Ticket $id delete transaction is started.")
        ticket.deleteWhere { ticket.id eq id }
    } > 0

    override suspend fun selectById(id: Long): Ticket? = DataBaseFactory.dbQuery {
        logger.info("Ticket $id select by id transaction is started.")
        ticket.select(ticket.id eq id).map(::ticketDBToTicketEntity).singleOrNull()
    }**/

  @Suppress("unused")
  fun Route.ticketRoute(){

      val ticketService = TicketTransactionImpl()

}