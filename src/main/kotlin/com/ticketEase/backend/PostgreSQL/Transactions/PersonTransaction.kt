package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Cities

interface PersonTransaction<T> : CRUDOperations<T,Long> {

    suspend fun updateCityPerson(id : Long, city : Cities) : Boolean

}