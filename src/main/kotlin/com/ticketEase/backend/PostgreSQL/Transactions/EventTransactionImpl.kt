package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Event.*
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory

class EventTransactionImpl : EventTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val event = EventTable

    private fun eventDBToEventEntity(rs : ResultRow) = EventDTO(
        id = rs[event.id].value,
        placeTimeId = rs[event.placeTimeId],
        organizerId = rs[event.organizerId],
        name = rs[event.name],
        genre = GenreList.valueOf(rs[event.genre]),
        type = TypeList.valueOf(rs[event.type]),
        status = StatusEvent.valueOf(rs[event.status]),
        nameGroup = rs[event.nameGroup],
        description = rs[event.description]
    )

    override suspend fun createEvent(eventDTO: EventDTO): EventDTO? = dbQuery{
        logger.info("Event create transaction is started.")
        val insertStatement = event.insert {
            it[event.placeTimeId] = eventDTO.placeTimeId
            it[event.organizerId] = eventDTO.organizerId
            it[event.name] = eventDTO.name
            it[event.genre] = eventDTO.genre.toString()
            it[event.type] = eventDTO.type.toString()
            it[event.status] = eventDTO.status.toString()
            it[event.nameGroup] = eventDTO.nameGroup
            it[event.description] = eventDTO.description
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::eventDBToEventEntity)
    }

    override suspend fun selectEventByGenreOrType(genre: GenreList, type: TypeList): List<EventDTO> = dbQuery {
        logger.info("Event select by genre and type transaction is started.")
        event.select{event.genre eq genre.toString();event.type eq type.toString()}.map(::eventDBToEventEntity)
    }

    override suspend fun selectGenreForPreferences(listEventId : List<Long>): List<String>  = dbQuery{
        logger.info("Event select genre transaction is started.")
        event.slice(event.genre).select{event.id inList listEventId}
            .orderBy(event.genre.count()).limit(5).map{it[event.genre]}.map{it}
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

