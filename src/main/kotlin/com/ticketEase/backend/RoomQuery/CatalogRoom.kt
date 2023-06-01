package com.ticketEase.backend.RoomQuery

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Person.Cities
import com.example.DataClasses.PlaceTable
import com.example.DataClasses.Ticket.TicketTable
import com.ticketEase.backend.DataClasses.Catalog
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import com.ticketEase.backend.PostgreSQL.Transactions.*
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class CatalogRoom {
    val event = EventTable
    val ticket = TicketTable
    val place = PlaceTable
    val placeTime = PlaceTimeTable
    val organizerService = OrganizerTransactionImpl()

    private fun toCatalogEntity(rs : ResultRow) = Catalog(
        name = rs[event.name],
        price = rs[ticket.price],
        location = rs[place.location],
        date = rs[placeTime.date]
    )

    suspend fun catalogRoom(city : Cities) : List<Catalog> = dbQuery{
        val listOrganizerId : List<Long> = organizerService.selectOrganizerByCity(city)
        place.join(placeTime,JoinType.INNER,place.id,placeTime.placeId)
            .join(event, JoinType.INNER,placeTime.id,event.placeTimeId, additionalConstraint = {
                event.organizerId inList listOrganizerId})
            .join(ticket,JoinType.INNER,event.id,ticket.eventId)
            .slice(event.name,ticket.price,place.location,placeTime.date)
            .selectAll().map(::toCatalogEntity)
    }



}