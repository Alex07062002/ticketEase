package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.NewPlace
import com.example.DataClasses.Place

interface PlaceTransaction : CRUDOperations<Place, Long> {

    suspend fun createPlace(place : NewPlace) : Place?

    suspend fun selectOneOfTypePlace(type: String) : List<Place>

    suspend fun updatePlace(placeId : Long, name : String?, capacity : Long?, numRow : Int?, numColumn : Int?) : Boolean

}