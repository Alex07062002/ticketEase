package com.example.DataClasses.Person

import kotlinx.serialization.Serializable

enum class Cities (val city : String) {
    Воронеж("Воронеж"),
    Москва("Москва"),
    Санкт_Петербург("Санкт-Петербург")
}

@Serializable
data class City(val city : String)
