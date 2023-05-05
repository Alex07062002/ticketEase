package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Cart
import java.sql.Timestamp
import java.time.Instant
import java.time.LocalDateTime
import java.time.temporal.TemporalAmount

interface CartTransaction : CRUDOperations<Cart, Pair<Long,Long>> {

    suspend fun  createCart(buyerId : Long, ticketId : Long) : Cart?

    suspend fun updateCart(buyerId : Long, ticketId : Long, orderDate : Timestamp) : Boolean

}