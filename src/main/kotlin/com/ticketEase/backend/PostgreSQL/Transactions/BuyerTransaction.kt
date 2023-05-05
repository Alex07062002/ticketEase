package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Buyer
import com.example.DataClasses.Person.Cities

interface BuyerTransaction : PersonTransaction<Buyer> {

    suspend fun updateParamsBuyer(buyerId : Long, name : String?, surname : String?, email : String?, mobile:String?):Boolean

    suspend fun createBuyer(name : String, surname: String, login : String,
                            password : String, email : String, mobile: String? = null, city : Cities) : Buyer?
}