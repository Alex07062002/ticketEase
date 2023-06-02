package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Cities

interface PersonTransaction<T> : CRUDOperations<T,Long> {

    suspend fun updateCityPerson(token : String, city : Cities) : Boolean

    suspend fun delete(token: String) : Boolean
}