package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Place

interface PlaceTransaction : CRUDOperations<Place, Long> {

    suspend fun createPlace(name : String, capacity: Long, numRow : Int?, numColumn : Int?) : Place?

    suspend fun selectOneOfTypePlace(type : TypeOfPlace, row : Int?, columns : Int?) : List<Place>

    suspend fun updatePlace(placeId : Long, name : String?, capacity : Long?, numRow : Int?, numColumn : Int?) : Boolean
}