package com.example.DataClasses.Ticket

enum class StatusTicket(val status : String){
    SALE("В продаже"),
    BOOK("Забронирован"),
    SOLD("Выкуплен"),
    DELETE("Отменен")
}

