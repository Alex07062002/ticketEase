package com.ticketEase.backend.DataClasses

import kotlinx.serialization.Serializable

@Serializable
data class TicketCountWithPrice(val eventId : Long,
                                val capacity : Long,
                                val row : Int? = null,
                                val column : Int? = null,
                                val price: Double)
