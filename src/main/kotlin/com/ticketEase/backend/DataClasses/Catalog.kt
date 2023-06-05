package com.ticketEase.backend.DataClasses

import java.time.Instant

data class Catalog(val eventId : Long,
                    val name : String,
                   val price : Double,
                   val location : String,
                   val date : Instant)
