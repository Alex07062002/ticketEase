package com.ticketEase.backend.PostgreSQL.DatabaseFactory

import com.example.DataClasses.CartTable
import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Favorites.FavoriteTable
import com.example.DataClasses.Person.organizer
import com.example.DataClasses.Person.OrganizerTable
import com.example.DataClasses.PlaceTable
import com.example.DataClasses.Ticket.TicketTable
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DataBaseFactory {

    fun init() {
        val driverClassName = DRIVER_CLASS_NAME
        val jdbcURL = JDBC_URL
        val user = USER
        val password = PASSWORD
        val database = Database.connect(jdbcURL, driverClassName,user, password)
        transaction(database) {
            SchemaUtils.create(organizer, CartTable, EventTable, FavoriteTable, OrganizerTable, PlaceTimeTable,
                PlaceTable, TicketTable)
        }
    }


    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}
