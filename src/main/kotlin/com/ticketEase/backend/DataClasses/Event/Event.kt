package com.example.DataClasses.Event

import com.example.DataClasses.Person.OrganizerTable
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.LongIdTable

@Serializable
data class Event(val id : Long,
                 val placeTimeId : Long? = null,
                 val organizerId : Long,
                 val name : String,
                 val genre : GenreList,
                 val type : TypeList,
                 val status : StatusEvent,
                 val nameGroup : String? = null,
                 val description : String? = null)

object EventTable : LongIdTable("event"){
    val eventId = long("id").autoIncrement().entityId()
    val placeTimeId = long("placeTime_id").references(PlaceTimeTable.placeTimeId).nullable()
    val organizerId = long("organizer_id").references(OrganizerTable.OrganizerId)
    val name = varchar("name", 100)
    val genre = enumeration("genre",GenreList::class)
    val type = enumeration("type",TypeList::class)
    val nameGroup = varchar("name_group",75).nullable()
    val description = varchar("description", 255).nullable()
    val status = enumeration("status",StatusEvent::class)
}