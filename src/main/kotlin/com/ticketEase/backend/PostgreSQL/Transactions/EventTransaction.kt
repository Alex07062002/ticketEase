package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Event.Event
import com.example.DataClasses.Event.GenreList
import com.example.DataClasses.Event.StatusEvent
import com.example.DataClasses.Event.TypeList
import com.example.DataClasses.Person.Buyer
import com.example.DataClasses.Person.Cities

interface EventTransaction :
    CRUDOperations<Event, Long> { // TODO использование одинаковых методов для разных Id (primary key, foreign key) -> потеря в семантике, выигрыш в объёме кода

        suspend fun createNewEvent(organizerId : Long,name : String,genre : GenreList,
                                   type : TypeList, status : StatusEvent = StatusEvent.CREATE): Event?

        suspend fun selectEventByGenreOrType(genre : GenreList, type : TypeList):List<Event> //TODO Bridge: eventId from placeTime by date and from ticket by cost

        suspend fun selectGenreForPreferences(buyerId : Long) : List<GenreList> //TODO Select genre from (table 4.1) order by count(genre) limit 5

        suspend fun selectEventByCity(city : Cities) : List<Event>?

        suspend fun selectEventByPlaceTime(placeTimeId : Long) : List<Event>?


}


