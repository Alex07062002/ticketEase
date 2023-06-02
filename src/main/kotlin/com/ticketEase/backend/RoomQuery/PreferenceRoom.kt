package com.ticketEase.backend.RoomQuery

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Event.StatusEvent
import com.example.DataClasses.Person.Cities
import com.example.DataClasses.PlaceTable
import com.example.DataClasses.Ticket.TicketTable
import com.ticketEase.backend.DataClasses.Catalog
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import com.ticketEase.backend.PostgreSQL.Transactions.BuyerTransactionImpl
import com.ticketEase.backend.PostgreSQL.Transactions.EventTransactionImpl
import com.ticketEase.backend.PostgreSQL.Transactions.OrganizerTransactionImpl
import com.ticketEase.backend.PostgreSQL.Transactions.TicketTransactionImpl
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll

class PreferenceRoom{
    private val event = EventTable
    private val ticket = TicketTable
    private val place = PlaceTable
    private val placeTime = PlaceTimeTable
    private val eventService = EventTransactionImpl()
    private val ticketService = TicketTransactionImpl()
    private val buyerService = BuyerTransactionImpl()
    private val organizerService = OrganizerTransactionImpl()

   private fun toCatalogEntity(rs : ResultRow) = Catalog(
        name = rs[event.name],
        price = rs[ticket.price],
        location = rs[place.location],
        date = rs[placeTime.date]
    )

    suspend fun preferencesRoom(token : String, city : Cities) = dbQuery {
        val buyerId : Long = buyerService.selectIdByToken(token)
        val listEventId : List<Long> = ticketService.selectEventByBuyer(buyerId)
        val genreList : List<String> = eventService.selectGenreForPreferences(listEventId)
        val listOrganizerId : List<Long> = organizerService.selectOrganizerByCity(city)
        place.join(placeTime, JoinType.INNER, place.id, placeTime.placeId)
            .join(event, JoinType.INNER, placeTime.id, event.placeTimeId, additionalConstraint = {
                event.organizerId inList listOrganizerId; event.genre inList genreList;
                event.status eq StatusEvent.CREATED.toString() })
            .join(ticket, JoinType.INNER, event.id, ticket.eventId)
            .slice(event.name, ticket.price, place.location, placeTime.date)
            .selectAll().map(::toCatalogEntity)
    }
}