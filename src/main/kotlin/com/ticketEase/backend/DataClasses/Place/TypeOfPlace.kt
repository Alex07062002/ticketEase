package com.ticketEase.backend.DataClasses.Place

import kotlinx.serialization.Serializable

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





