package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Cities
import com.example.DataClasses.PlaceDTO
import com.ticketEase.backend.DataClasses.Place.TypeOfPlace

interface PlaceTransaction : CRUDOperations<PlaceDTO, Long> {

    suspend fun createPlace(place : PlaceDTO) : PlaceDTO?

    suspend fun selectOneOfTypePlace(type: TypeOfPlace, city : Cities) : List<PlaceDTO>

    suspend fun updatePlace(updatePlace : PlaceDTO) : PlaceDTO?

    suspend fun delete(id : Long) : Boolean

    suspend fun capacity(id: Long) : Long

}