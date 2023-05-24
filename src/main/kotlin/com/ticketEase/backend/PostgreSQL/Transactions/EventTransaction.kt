package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Event.EventDTO
import com.example.DataClasses.Event.GenreList
import com.example.DataClasses.Event.StatusEvent
import com.example.DataClasses.Event.TypeList
import com.example.DataClasses.Person.Cities

interface EventTransaction :
    CRUDOperations<EventDTO, Long> {

        suspend fun createEvent(eventDTO: EventDTO): EventDTO?

        suspend fun selectEventByGenreOrType(genre : GenreList, type : TypeList):List<EventDTO> //TODO Bridge: eventId from placeTime by date and from ticket by cost

        suspend fun selectGenreForPreferences(buyerId : Long) : List<GenreList> //TODO Select genre from (table 4.1) order by count(genre) limit 5

        suspend fun selectEventByCity(city : Cities) : List<EventDTO>?

        suspend fun selectEventByPlaceTime(placeTimeId : Long) : List<EventDTO>?

        suspend fun delete(id : Long) : Boolean

}


