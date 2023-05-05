package com.ticketEase.backend.DataClasses.PlaceTime

import com.example.DataClasses.PlaceTable
import com.ticketEase.backend.DataClasses.DateSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.util.Calendar

@Serializable
data class PlaceTime(val id : Long,
                     val placeId : Long,
                     @Serializable(with = DateSerializer::class)
                     val date : Instant,
                     val status : StatusPlaceTime)

object PlaceTimeTable : LongIdTable("placeTime") {
    val placeTimeId = long("placeTime_id").autoIncrement().entityId()
    val placeId = long("place_id").references(PlaceTable.placeId)
    val date = timestamp("date").uniqueIndex()
    val status = enumeration("status", StatusPlaceTime::class)
}
