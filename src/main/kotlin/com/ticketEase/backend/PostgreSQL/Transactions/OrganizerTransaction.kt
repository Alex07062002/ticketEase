package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Cities
import com.example.DataClasses.Person.Organizer
import com.example.DataClasses.Person.StatusOrganizer
import org.jetbrains.exposed.sql.Query

interface OrganizerTransaction : PersonTransaction<Organizer> {

    suspend fun updateParamsOrganizer(organizerId : Long, name : String?, surname : String?, email : String?,
                                      mobile:String?, status : StatusOrganizer?):Boolean

    suspend fun createOrganizer(name : String, surname: String, login : String,
                                password : String, email : String, mobile: String? = null,
                                city : Cities, status : StatusOrganizer) : Organizer?

    suspend fun selectOrganizerByCity(city : Cities) : Query
}