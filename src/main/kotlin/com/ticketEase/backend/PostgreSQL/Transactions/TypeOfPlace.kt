package com.ticketEase.backend.PostgreSQL.Transactions

enum class TypeOfPlace {
    WITH,
    WITHOUT;

    override fun toString(): String {
        return if (this == WITH) {
            "with"
        } else{
            "without"
        }
    }
}



