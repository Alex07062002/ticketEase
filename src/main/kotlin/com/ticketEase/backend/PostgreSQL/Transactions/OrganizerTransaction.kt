package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.*
import org.jetbrains.exposed.sql.Query

interface OrganizerTransaction : PersonTransaction<Organizer> {

    suspend fun updateParamsOrganizer(organizer: OrganizerWithoutPswd):OrganizerWithoutPswd?

    suspend fun createOrganizer(organizer: Organizer) : Organizer?

    suspend fun selectOrganizerByCity(city : Cities) : Query // TODO change this

    suspend fun selectByLogin(login : String) : Organizer?

    suspend fun selectByToken(token : String) : OrganizerWithoutPswd?

}