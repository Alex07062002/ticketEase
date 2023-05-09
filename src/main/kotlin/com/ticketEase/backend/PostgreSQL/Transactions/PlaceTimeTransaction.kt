package com.ticketEase.backend.PostgreSQL.Transactions

import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTime
import com.ticketEase.backend.DataClasses.PlaceTime.StatusPlaceTime
import java.time.Instant

interface PlaceTimeTransaction : CRUDOperations<PlaceTime, Long> {
    suspend fun createPlaceTime(placeId: Long, date : Instant, status: StatusPlaceTime) : PlaceTime?

    suspend fun selectByPlace(placeId : Long) : List<PlaceTime>

    suspend fun updatePlaceTime(placeTimeId : Long, status : StatusPlaceTime) : Boolean

    suspend fun selectIdByDate(date : Instant) : List<PlaceTime>

    suspend fun selectDateById(placeTimeId : Long) : Instant?
}