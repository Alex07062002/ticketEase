package com.ticketEase.backend.PostgreSQL.Transactions

interface CRUDOperations<T,E> {

    suspend fun selectAll() : List<T>
    suspend fun selectById(id : E) : T?
}


