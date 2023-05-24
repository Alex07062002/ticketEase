package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.CartDTO
import java.sql.Timestamp

interface CartTransaction : CRUDOperations<CartDTO, Pair<Long,Long>> {

    suspend fun  createCart(cart : CartDTO) : CartDTO?

    suspend fun updateCart(cart : CartDTO) : CartDTO?

    suspend fun delete(id : Pair<Long,Long>) : Boolean


}