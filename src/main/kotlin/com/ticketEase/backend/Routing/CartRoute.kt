package com.ticketEase.backend.Routing

import com.example.DataClasses.CartDTO
import com.ticketEase.backend.PostgreSQL.Transactions.CartTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("unused")
fun Route.cartRoute(){

    val cartService  = CartTransactionImpl()

route("/carts"){
    post{
        call.respond(HttpStatusCode.OK, cartService.selectAll())
    }
    post("/{buyerId,ticketId}"){ //TODO Fix method!!!
        val buyerIdFromQuery = call.parameters["buyerId"] ?: kotlin.run {
            throw NotFoundException("Please provide a valid buyer id")
        }
        val ticketIdFromQuery = call.parameters["ticketId"] ?: kotlin.run {
            throw NotFoundException("Please provide a valid ticket id")
        }
        val cart = cartService.selectById(Pair(buyerIdFromQuery.toLong(),ticketIdFromQuery.toLong()))
        if (cart == null) call.respond(
            HttpStatusCode.NotFound,
            "Cart isn't find."
        ) else call.respond(HttpStatusCode.OK, cart)
    }
    delete("/{buyerId,ticketId}") {
        val buyerIdFromQuery = call.parameters["buyerId"] ?: kotlin.run {
            throw NotFoundException("Please provide a valid buyer id")
        }
        val ticketIdFromQuery = call.parameters["ticketId"] ?: kotlin.run {
            throw NotFoundException("Please provide a valid ticket id")
        }
        cartService.delete(Pair(buyerIdFromQuery.toLong(),ticketIdFromQuery.toLong()))
        call.respond("Cart is deleted.")
    }
    post("/create"){
        val parameters = call.receive<CartDTO>()
        val cart = cartService.createCart(parameters)
        if (cart == null) call.respond(HttpStatusCode.BadRequest,"Cart isn't created.") else
            call.respond(HttpStatusCode.OK,cart)
    }
    put("/{{buyerId,eventId}/update"){
        val parameters = call.receive<CartDTO>()
        val cart = cartService.updateCart(parameters)
        if (cart == null) call.respond(HttpStatusCode.BadRequest,"Cart isn't updated.") else
            call.respond(HttpStatusCode.OK,cart)
    }
}
}