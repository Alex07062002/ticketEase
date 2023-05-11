package com.example.DataClasses.Event

import com.example.DataClasses.Person.OrganizerTable
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class EventDTO(val id : Long?,
                    val placeTimeId : Long,
                    val organizerId : Long,
                    val name : String,
                    val genre : GenreList,
                    val type : TypeList,
                    val status : StatusEvent = StatusEvent.CREATE,
                    val nameGroup : String? = null,
                    val description : String? = null)

object EventTable : LongIdTable("event"){
    val placeTimeId = long("placeTime_id").references(PlaceTimeTable.id)
    val organizerId = long("organizer_id").references(OrganizerTable.id)
    val name = varchar("name", 100)
    val genre = enumeration("genre",GenreList::class)
    val type = enumeration("type",TypeList::class)
    val nameGroup = varchar("name_group",75).nullable()
    val description = varchar("description", 255).nullable()
    val status = enumeration("status",StatusEvent::class)
}