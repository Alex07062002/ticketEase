package com.ticketEase.backend.Routing

import com.example.DataClasses.Event.Event
import com.example.DataClasses.Event.GenreList
import com.example.DataClasses.Event.StatusEvent
import com.example.DataClasses.Event.TypeList
import com.example.DataClasses.Person.Cities
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory
import com.ticketEase.backend.PostgreSQL.Transactions.OrganizerTransactionImpl
import com.ticketEase.backend.PostgreSQL.Transactions.TicketTransactionImpl
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inSubQuery

/**
override suspend fun createEvent(
    organizerId: Long,
    name: String,
    genre: GenreList,
    type: TypeList,
    status: StatusEvent
): Event? = DataBaseFactory.dbQuery {
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

override suspend fun selectEventByGenreOrType(genre: GenreList, type: TypeList): List<Event> = DataBaseFactory.dbQuery {
    logger.info("Event select by genre and type transaction is started.")
    event.select { event.genre eq genre;event.type eq type }.map(::eventDBToEventEntity)
}

override suspend fun selectGenreForPreferences(buyerId : Long): List<GenreList>  = DataBaseFactory.dbQuery {
    logger.info("Event select genre transaction is started.")
    event.slice(event.genre).select { event.id inSubQuery (TicketTransactionImpl().selectEventByBuyer(buyerId)) }
        .orderBy(event.genre.count()).limit(5).map { it[event.genre] }
}

override suspend fun selectEventByCity(city: Cities): List<Event>  = DataBaseFactory.dbQuery {
    logger.info("Event select by city $city transaction is started.")
    event.select(event.organizerId inSubQuery (OrganizerTransactionImpl().selectOrganizerByCity(city)))
        .orderBy(event.placeTimeId to SortOrder.ASC)
        .map(::eventDBToEventEntity)
} // TODO change orderBy

override suspend fun selectEventByPlaceTime(placeTimeId: Long): List<Event>  = DataBaseFactory.dbQuery {
    logger.info("Event select by placeTime id $placeTimeId transaction is started.")
    event.select(event.placeTimeId eq placeTimeId).map(::eventDBToEventEntity)
}

override suspend fun selectAll(): List<Event>  = DataBaseFactory.dbQuery {
    logger.info("Event select all transaction is started.")
    event.selectAll().map(::eventDBToEventEntity)
}

override suspend fun delete(id: Long): Boolean  = DataBaseFactory.dbQuery {
    logger.info("Event $id delete transaction is started.")
    event.deleteWhere { event.id eq id }
} > 0

override suspend fun selectById(id: Long): Event?  = DataBaseFactory.dbQuery {
    logger.info("Event $id select by id transaction is started.")
    event.select(event.id eq id).map(::eventDBToEventEntity).singleOrNull()
}
        **/

@Suppress("unused")
fun Route.eventRoute(){

}