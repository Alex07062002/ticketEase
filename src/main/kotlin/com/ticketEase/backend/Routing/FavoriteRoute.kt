package com.ticketEase.backend.Routing

import com.example.DataClasses.Favorites.FavoriteDTO
import com.example.DataClasses.Favorites.FavoriteWithoutStatus
import com.example.DataClasses.Person.BuyerId
import com.ticketEase.backend.PostgreSQL.Transactions.FavoriteTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
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
        post("/buyerId/eventId"){
            val parameters = call.receive<FavoriteWithoutStatus>()
            val favorite = favoriteService.selectById(Pair(parameters.buyerId,parameters.eventId))
            if (favorite == null) call.respond(
                HttpStatusCode.NotFound,
                "Favorite isn't find."
            ) else call.respond(HttpStatusCode.OK, favorite)
        }
        delete("/buyerId/eventId") {
            val parameters = call.receive<FavoriteWithoutStatus>()
            favoriteService.delete(Pair(parameters.buyerId,parameters.eventId))
            call.respond("Favorite is deleted.")
        }
        post("/create"){
            val parameters = call.receive<FavoriteDTO>()
            val favorite = favoriteService.createFavorite(parameters)
            if (favorite == null) call.respond(HttpStatusCode.BadRequest,"Favorite isn't created.") else
                call.respond(HttpStatusCode.Created,favorite)
        }
        put("/update"){
            val parameters = call.receive<FavoriteDTO>()
            val favorite = favoriteService.updateFavorite(parameters)
            if (favorite == null) call.respond(HttpStatusCode.BadRequest,"Favorite isn't updated.") else
                call.respond(HttpStatusCode.OK,favorite)
        }
        post("/buyerId"){
            val parameters = call.receive<BuyerId>()
            val favoriteList = favoriteService.selectEventFromFavorite(parameters.id)
            call.respond(HttpStatusCode.OK,favoriteList)
        }
    }
}