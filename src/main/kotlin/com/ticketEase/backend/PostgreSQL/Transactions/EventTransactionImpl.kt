package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Event.*
import com.example.DataClasses.Person.Cities
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inSubQuery
import org.slf4j.LoggerFactory

class EventTransactionImpl : EventTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val event = EventTable

    private fun eventDBToEventEntity(rs : ResultRow) = Event(
        id = rs[event.eventId].value,
        placeTimeId = rs[event.placeTimeId],
        organizerId = rs[event.organizerId],
        name = rs[event.name],
        genre = rs[event.genre],
        type = rs[event.type],
        status = rs[event.status],
        nameGroup = rs[event.nameGroup],
        description = rs[event.description]
    )

    override suspend fun createNewEvent(
        organizerId: Long,
        name: String,
        genre: GenreList,
        type: TypeList,
        status: StatusEvent
    ): Event? = dbQuery{
        logger.info("Event create transaction is started.")
        val insertStatement = event.insert {
            it[event.organizerId] = organizerId
            it[event.name] = name
            it[event.genre] = genre
            it[event.type] = type
            it[event.status] = status
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::eventDBToEventEntity)
    }

    override suspend fun selectEventByGenreOrType(genre: GenreList, type: TypeList): List<Event> = dbQuery {
        logger.info("Event select by genre and type transaction is started.")
        event.select{event.genre eq genre;event.type eq type}.map(::eventDBToEventEntity)
    }

    override suspend fun selectGenreForPreferences(buyerId : Long): List<GenreList>  = dbQuery{
        logger.info("Event select genre transaction is started.")
        event.slice(event.genre).select{event.eventId inSubQuery (TicketTransactionImpl().selectEventByBuyer(buyerId))}
            .orderBy(event.genre.count()).limit(5).map{it[event.genre]}
    }

    override suspend fun selectEventByCity(city: Cities): List<Event>  = dbQuery{
        logger.info("Event select by city transaction is started.")
        event.select(event.organizerId inSubQuery (OrganizerTransactionImpl().selectOrganizerByCity(city)))
            .map(::eventDBToEventEntity)
    }

    override suspend fun selectEventByPlaceTime(placeTimeId: Long): List<Event>  = dbQuery{
        logger.info("Event select by date transaction is started.")
        event.select(event.placeTimeId eq placeTimeId).map(::eventDBToEventEntity)
    }

    override suspend fun selectAll(): List<Event>  = dbQuery{
    logger.info("Event select all transaction is started.")
    event.selectAll().map(::eventDBToEventEntity)
    }

    override suspend fun delete(id: Long): Boolean  = dbQuery{
        logger.info("Event delete transaction is started.")
        event.deleteWhere { event.eventId eq id}
    } > 0

    override suspend fun selectById(id: Long): Event?  = dbQuery{
        logger.info("Event select by id transaction is started.")
        event.select(event.eventId eq id).map(::eventDBToEventEntity).singleOrNull()
    }
}

