package com.ticketEase.backend.PostgreSQL.Transactions.` Adapters`

import java.sql.Timestamp
import java.time.Instant

object DateAdapter {
    fun instantToTimestamp(date : Instant) : Timestamp =  Timestamp(date.toEpochMilli())

    fun timestampToInstant(date : Timestamp) : Instant = Instant.ofEpochMilli(date.time)
}