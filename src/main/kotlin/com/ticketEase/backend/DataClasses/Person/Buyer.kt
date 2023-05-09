package com.example.DataClasses.Person

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable


@Serializable
data class Buyer(val id : Long,
                 val name : String,
                 val surname : String,
                 val login : String,
                 val password : String,
                 val email: String,
                 val mobile : String? = null,
                 val city : Cities)

object BuyerTable : LongIdTable("buyer"){
    val name = varchar("name", 25)
    val surname = varchar("surname", 50)
    val login = varchar("login",50).uniqueIndex()
    val password = varchar("password",25)
    val email = varchar("email",40)
    val mobile = varchar("mobile",25).nullable()
    val city = enumeration("city",Cities::class)
}
