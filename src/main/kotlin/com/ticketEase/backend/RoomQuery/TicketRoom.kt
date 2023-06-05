package com.ticketEase.backend.RoomQuery

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.PlaceTable
import com.example.DataClasses.Ticket.TicketTable
import com.ticketEase.backend.DataClasses.Catalog
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class TicketRoom {
    private val event = EventTable
    private val ticket = TicketTable
    private val place = PlaceTable
    private val placeTime = PlaceTimeTable

    private fun toCatalogEntity(rs : ResultRow) = Catalog(
        eventId = rs[event.id].value,
        name = rs[event.name],
        price = rs[ticket.price],
        location = rs[place.location],
        date = rs[placeTime.date]
    )

    suspend fun ticketRoom(eventId : Long) : Catalog = DataBaseFactory.dbQuery {
        place.join(placeTime, JoinType.INNER, place.id, placeTime.placeId)
            .join(event, JoinType.INNER, placeTime.id, event.placeTimeId, additionalConstraint = {
                event.id eq eventId })
            .join(ticket, JoinType.INNER, event.id, ticket.eventId)
            .slice(event.id, event.name, ticket.price, place.location, placeTime.date).selectAll()
            .map(::toCatalogEntity)[0]
    }
}