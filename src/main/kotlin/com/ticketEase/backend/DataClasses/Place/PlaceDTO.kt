package com.example.DataClasses

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class PlaceDTO(
    val id : Long? = null,
    val name : String,
    val capacity: Long,
    val numRow: Int?,
    val numColumn: Int?,
    val location : String
)

@Serializable
data class PlaceId(val id : Long)

object PlaceTable : LongIdTable("place"){
    val name = varchar("name", 50)
    val capacity = long("capacity")
    val numRow = integer("numRow").nullable()
    val numColumn = integer("numColumn").nullable()
    val location = varchar("location", 150)
}

