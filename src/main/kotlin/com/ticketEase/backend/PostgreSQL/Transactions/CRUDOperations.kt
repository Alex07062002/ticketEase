package com.ticketEase.backend.PostgreSQL.Transactions

interface CRUDOperations<T,E> {
    /**
     *  interest realization repository in kotlite/kotgres
     *  See more: "https://mfarsikov.github.io/kotgres/"
    **/
    suspend fun selectAll() : List<T>
    suspend fun selectById(id : E) : T?
}