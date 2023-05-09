package com.example.DataClasses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable


@Serializable
data class Place(
    val id : Long,
    val name : String,
    val capacity : Long,
    val numRow : Int?,
    val numColumn : Int?)

@Serializable
data class NewPlace( // TODO Bad practice
    val id : Long? = null,
    val name : String,
    val capacity : Long,
    val numRow : Int? = null,
    val numColumn : Int? = null)

object PlaceTable : LongIdTable("place"){
    val name = varchar("name", 50)
    val capacity = long("capacity")
    val numRow = integer("numRow").nullable()
    val numColumn = integer("numColumn").nullable()
}

