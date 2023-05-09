package com.ticketEase.backend.Routing

import io.ktor.server.routing.*

/**
 *     override suspend fun selectEventFromFavorite(buyerId: Long): List<Long> = dbQuery {
 *         logger.info("Favorite select event id by buyer id $buyerId transaction is started.")
 *         favorite.slice(favorite.eventId).select(favorite.buyerId eq buyerId).map{it[favorite.eventId]}
 *     }
 *
 *     override suspend fun updateFavorite(buyerId: Long, eventId: Long, statusFav: StatusFavorite): Boolean = dbQuery {
 *         logger.info("Favorite $buyerId $eventId update transaction is started.")
 *         favorite.update({ favorite.buyerId eq buyerId;favorite.eventId eq eventId }) {
 *                 it[status] = statusFav
 *         }
 *     } > 0
 *
 *     override suspend fun selectAll(): List<Favorite>  = dbQuery{
 *         logger.info("Favorite select all transaction is started.")
 *         favorite.selectAll().map(::favoriteDBToFavoriteEntity)
 *     }
 *
 *     override suspend fun delete(id: Pair<Long, Long>): Boolean = dbQuery {
 *         logger.info("Favorite ${id.first} ${id.second} delete transaction is started.")
 *         favorite.deleteWhere{favorite.buyerId eq id.first;favorite.eventId eq id.second}
 *     } > 0
 *
 *     override suspend fun selectById(id: Pair<Long, Long>): Favorite? = dbQuery {
 *         logger.info("Favorite ${id.first} ${id.second} select by id transaction is started.")
 *         favorite.select{favorite.buyerId eq id.first; favorite.eventId eq id.second}
 *             .map(::favoriteDBToFavoriteEntity).singleOrNull()
 *     }
 */

@Suppress("unused")
fun Route.favoriteRoute(){

}