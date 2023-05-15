package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Buyer
import com.example.DataClasses.Person.BuyerRequest
import com.example.DataClasses.Person.Cities
import com.example.DataClasses.Person.Organizer

interface BuyerTransaction : PersonTransaction<Buyer> {

    suspend fun updateParamsBuyer(buyer: Buyer):Buyer?

    suspend fun createBuyer(buyer: Buyer) : Buyer?


    suspend fun selectByLogin(login : String) : Buyer?


}