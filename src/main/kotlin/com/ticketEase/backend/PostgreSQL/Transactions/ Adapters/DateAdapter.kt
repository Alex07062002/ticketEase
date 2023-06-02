package com.ticketEase.backend.PostgreSQL.Transactions.` Adapters`

import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Deprecated("Change to JavaTimeModule serialization")
object DateAdapter {
    fun instantToTimestamp(date : Instant) : Timestamp =  Timestamp(date.toEpochMilli())

    fun timestampToInstant(date : Timestamp) : Instant = Instant.ofEpochMilli(date.time)

    fun stringToInstant(date : String) : Instant = LocalDateTime.parse(date,
        DateTimeFormatter.ofPattern( "hh:mm, MM/dd/yyyy")).toInstant(null)
}