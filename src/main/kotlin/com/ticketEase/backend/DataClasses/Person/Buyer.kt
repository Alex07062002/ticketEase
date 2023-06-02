package com.example.DataClasses.Person

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class BuyerRequest(val login: String,val password: String)
@Serializable
data class BuyerResponse(val token : String)
@Serializable
data class BuyerWithoutPswd(val id : Long, val name : String,val surname: String,val email: String, val mobile: String? = null, val token: String)
@Serializable
data class BuyerCity(val token: String, val city : Cities)
@Serializable
data class BuyerLogin(val login: String)
@Serializable
data class Buyer(val id : Long,
                 val name : String,
                 val surname : String,
                 val login : String,
                 val password : String,
                 val email: String,
                 val mobile : String? = null,
                 val city : Cities,
                 val secret : String? = null)

@Serializable
data class BuyerId(val id : Long)

object BuyerTable : LongIdTable("buyer"){
    val name = varchar("name", 25)
    val surname = varchar("surname", 50)
    val login = varchar("login",50).uniqueIndex()
    val password = varchar("password",75)
    val email = varchar("email",40)
    val mobile = varchar("mobile",25).nullable()
    val city = varchar("city",30)
    val secret = varchar("secret", 75)
}
