package com.example.DataClasses.Person

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable
@Serializable
data class OrganizerRequest(val login: String,val password: String)
@Serializable
data class OrganizerResponse(val token : String)
@Serializable
data class OrganizerWithoutPswd(val id : Long, val name : String, val surname: String, val email: String,
                                val mobile: String? = null, val status: StatusOrganizer, val token: String)
@Serializable
data class OrganizerUpdateCity(val token: String, val city : Cities)
@Serializable
data class Organizer(
    val id: Long,
    val name: String,
    val surname: String,
    val login: String,
    val password: String,
    val email: String,
    val mobile: String? = null,
    val city: Cities,
    val status: StatusOrganizer,
    val secret : String? = null)

object OrganizerTable : LongIdTable("organizer"){
    val name = varchar("name", 25)
    val surname = varchar("surname", 50)
    val login = varchar("login", 50).uniqueIndex()
    val password = varchar("password", 75)
    val email = varchar("email",40)
    val mobile = varchar("mobile", 20).nullable()
    val city = varchar("city",50)
    val status = varchar("status", 30)
    val secret = varchar("secret", 75)
}