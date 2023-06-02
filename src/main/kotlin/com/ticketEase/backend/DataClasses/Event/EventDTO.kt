package com.example.DataClasses.Event

import com.example.DataClasses.Person.OrganizerTable
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class EventDTO(val id : Long? = null,
                    val placeTimeId : Long,
                    val organizerId : Long,
                    val name : String,
                    val genre : GenreList,
                    val type : TypeList,
                    val status : StatusEvent = StatusEvent.CREATED,
                    val nameGroup : String? = null,
                    val description : String? = null)

@Serializable
data class EventId(val id : Long)

object EventTable : LongIdTable("event"){
    val placeTimeId = long("placeTime_id").references(PlaceTimeTable.id)
    val organizerId = long("organizer_id").references(OrganizerTable.id)
    val name = varchar("name", 100)
    val genre = varchar("genre",50)
    val type = varchar("type",50)
    val nameGroup = varchar("name_group",75).nullable()
    val description = varchar("description", 255).nullable()
    val status = varchar("status",30)
}