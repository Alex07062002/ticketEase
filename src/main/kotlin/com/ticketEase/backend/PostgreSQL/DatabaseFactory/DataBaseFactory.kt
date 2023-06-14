package com.ticketEase.backend.PostgreSQL.DatabaseFactory

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Favorites.FavoriteTable
import com.example.DataClasses.Person.OrganizerTable
import com.example.DataClasses.PlaceTable
import com.example.DataClasses.Ticket.TicketTable
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DataBaseFactory {

    fun init(config : ApplicationConfig) {
        val driverClassName = config.property("storage.driverClassName").getString()
        val jdbcURL = config.property("storage.jdbcURL").getString()
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
            SchemaUtils.create(OrganizerTable, EventTable, FavoriteTable, OrganizerTable, PlaceTimeTable,
                PlaceTable, TicketTable)
        }
    }


    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }

}
