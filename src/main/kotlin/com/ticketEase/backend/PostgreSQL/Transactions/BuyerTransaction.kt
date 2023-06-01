package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.*

interface BuyerTransaction : PersonTransaction<Buyer> {

    suspend fun updateParamsBuyer(buyer: BuyerWithoutPswd):BuyerWithoutPswd?

    suspend fun createBuyer(buyer: Buyer) : BuyerWithoutPswd?

    suspend fun selectByLogin(login : String) : Buyer?

    suspend fun checkByLogin(login : String) : Boolean

    suspend fun selectByToken(token : String) : BuyerWithoutPswd?

    suspend fun selectIdByToken(token: String) : Long

}