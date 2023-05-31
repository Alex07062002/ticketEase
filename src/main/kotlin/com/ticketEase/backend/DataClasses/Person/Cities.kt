package com.example.DataClasses.Person

import kotlinx.serialization.Serializable

enum class Cities (val city : String) {
    Voronezh("Воронеж"),
    Moscow("Москва"),
    SaintPetersburg("Санкт-Петербург")
}

@Serializable
data class City(val city : Cities)
