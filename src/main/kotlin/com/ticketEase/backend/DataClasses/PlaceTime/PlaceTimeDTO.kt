package com.ticketEase.backend.DataClasses.PlaceTime

import com.example.DataClasses.PlaceTable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

data class PlaceTimeDTO(val id: Long? = null,
                        val placeId : Long,
                        val date : Instant,
                        val status : StatusPlaceTime)

data class PlaceTimeId(val id : Long)

data class PlaceTimeDate(
    val date : Instant
)

object PlaceTimeTable : LongIdTable("placeTime") {
    val placeId = long("place_id").references(PlaceTable.id)
    val date = timestamp("date")
    val status = varchar("status",30)
}
