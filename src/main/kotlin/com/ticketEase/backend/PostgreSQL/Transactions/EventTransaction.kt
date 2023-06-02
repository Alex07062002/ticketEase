package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Event.EventDTO
import com.example.DataClasses.Event.GenreList
import com.example.DataClasses.Event.TypeList

interface EventTransaction :
    CRUDOperations<EventDTO, Long> {

        suspend fun createEvent(eventDTO: EventDTO): EventDTO?

        suspend fun selectEventByGenreOrType(genre : GenreList, type : TypeList):List<EventDTO>

        suspend fun selectGenreForPreferences(listEventId : List<Long>) : List<String>

        suspend fun selectEventByPlaceTime(placeTimeId : Long) : List<EventDTO>?

        suspend fun delete(id : Long) : Boolean

}


