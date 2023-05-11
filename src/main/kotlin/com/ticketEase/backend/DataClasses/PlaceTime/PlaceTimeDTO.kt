package com.ticketEase.backend.DataClasses.PlaceTime

import com.example.DataClasses.PlaceTable
import com.ticketEase.backend.DataClasses.DateSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.time.Instant

@Serializable
data class PlaceTimeDTO(val id: Long?,
                        val placeId : Long,
                        @Serializable(with = DateSerializer::class)
                        val date : Instant,
                        val status : StatusPlaceTime)

object PlaceTimeTable : LongIdTable("placeTime") {
    val placeId = long("place_id").references(PlaceTable.id)
    val date = timestamp("date")
    val status = enumeration("status", StatusPlaceTime::class)
}
