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

    private fun eventDBToEventEntity(rs : ResultRow) = EventDTO(
        id = rs[event.id].value,
        placeTimeId = rs[event.placeTimeId],
        organizerId = rs[event.organizerId],
        name = rs[event.name],
        genre = rs[event.genre],
        type = rs[event.type],
        status = rs[event.status],
        nameGroup = rs[event.nameGroup],
        description = rs[event.description]
    )

    override suspend fun createEvent(eventDTO: EventDTO): EventDTO? = dbQuery{
        logger.info("Event create transaction is started.")
        val insertStatement = event.insert {
            it[event.placeTimeId] = eventDTO.placeTimeId
            it[event.organizerId] = eventDTO.organizerId
            it[event.name] = eventDTO.name
            it[event.genre] = eventDTO.genre
            it[event.type] = eventDTO.type
            it[event.status] = eventDTO.status
            it[event.nameGroup] = eventDTO.nameGroup
            it[event.description] = eventDTO.description
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::eventDBToEventEntity)
    }

    override suspend fun selectEventByGenreOrType(genre: GenreList, type: TypeList): List<EventDTO> = dbQuery {
        logger.info("Event select by genre and type transaction is started.")
        event.select{event.genre eq genre;event.type eq type}.map(::eventDBToEventEntity)
    }

    override suspend fun selectGenreForPreferences(buyerId : Long): List<GenreList>  = dbQuery{
        logger.info("Event select genre transaction is started.")
        event.slice(event.genre).select{event.id inSubQuery (TicketTransactionImpl().selectEventByBuyer(buyerId))}
            .orderBy(event.genre.count()).limit(5).map{it[event.genre]}
    }

    override suspend fun selectEventByCity(city: Cities): List<EventDTO>  = dbQuery{
        logger.info("Event select by city $city transaction is started.")
        event.select(event.organizerId inSubQuery (OrganizerTransactionImpl().selectOrganizerByCity(city)))
            .orderBy(event.placeTimeId to SortOrder.ASC)
            .map(::eventDBToEventEntity)
    }

    override suspend fun selectEventByPlaceTime(placeTimeId: Long): List<EventDTO>  = dbQuery{
        logger.info("Event select by placeTime id $placeTimeId transaction is started.")
        event.select(event.placeTimeId eq placeTimeId).map(::eventDBToEventEntity)
    }

    override suspend fun selectAll(): List<EventDTO>  = dbQuery{
    logger.info("Event select all transaction is started.")
    event.selectAll().map(::eventDBToEventEntity)
    }

    override suspend fun delete(id: Long): Boolean  = dbQuery{
        logger.info("Event $id delete transaction is started.")
        event.deleteWhere { event.id eq id}
    } > 0

    override suspend fun selectById(id: Long): EventDTO?  = dbQuery{
        logger.info("Event $id select by id transaction is started.")
        event.select(event.id eq id).map(::eventDBToEventEntity).singleOrNull()
    }
}

