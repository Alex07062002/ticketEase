package com.ticketEase.backend.Routing

import com.example.DataClasses.Favorites.FavoriteDTO
import com.ticketEase.backend.PostgreSQL.Transactions.FavoriteTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Suppress("unused")
fun Route.favoriteRoute(){

    val favoriteService  = FavoriteTransactionImpl()

    route("/favorites"){
        post{
            call.respond(HttpStatusCode.OK, favoriteService.selectAll())
        }
        post("/{buyerId,eventId}"){ //TODO Fix method!!!
            val buyerIdFromQuery = call.parameters["buyerId"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid buyer id")
            }
            val eventIdFromQuery = call.parameters["eventId"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid event id")
            }
            val favorite = favoriteService.selectById(Pair(buyerIdFromQuery.toLong(),eventIdFromQuery.toLong()))
            if (favorite == null) call.respond(
                HttpStatusCode.NotFound,
                "Favorite isn't find."
            ) else call.respond(HttpStatusCode.OK, favorite)
        }
        delete("/{buyerId,eventId}") {
            val buyerIdFromQuery = call.parameters["buyerId"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid buyer id")
            }
            val eventIdFromQuery = call.parameters["eventId"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid event id")
            }
            favoriteService.delete(Pair(buyerIdFromQuery.toLong(),eventIdFromQuery.toLong()))
            call.respond("Favorite is deleted.")
        }
        post("/create"){
            val parameters = call.receive<FavoriteDTO>()
            val favorite = favoriteService.createFavorite(parameters)
            if (favorite == null) call.respond(HttpStatusCode.BadRequest,"Favorite isn't created.") else
                call.respond(HttpStatusCode.Created,favorite)
        }
        put("/{{buyerId,eventId}/update"){
            val parameters = call.receive<FavoriteDTO>()
            val favorite = favoriteService.updateFavorite(parameters)
            if (favorite == null) call.respond(HttpStatusCode.BadRequest,"Favorite isn't updated.") else
                call.respond(HttpStatusCode.OK,favorite)
        }
        post("/{buyerId}"){
            val buyerId = call.parameters["buyerId"] ?: kotlin.run{
                throw NotFoundException("Not found buyer id")
            }
            val favoriteList = favoriteService.selectEventFromFavorite(buyerId.toLong())
            call.respond(HttpStatusCode.OK,favoriteList)
        }
    }
}