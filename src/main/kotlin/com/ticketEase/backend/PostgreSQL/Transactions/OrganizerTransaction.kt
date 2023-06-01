package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.*

interface OrganizerTransaction : PersonTransaction<Organizer> {

    suspend fun updateParamsOrganizer(organizer: OrganizerWithoutPswd):OrganizerWithoutPswd?

    suspend fun createOrganizer(organizer: Organizer) : OrganizerWithoutPswd?

    suspend fun selectOrganizerByCity(city : Cities) : List<Long>

    suspend fun selectByLogin(login : String) : Organizer?

    suspend fun selectByToken(token : String) : OrganizerWithoutPswd?

}