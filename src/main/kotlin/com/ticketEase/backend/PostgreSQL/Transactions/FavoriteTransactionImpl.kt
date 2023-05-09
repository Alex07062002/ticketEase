package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Favorites.Favorite
import com.example.DataClasses.Favorites.FavoriteTable
import com.example.DataClasses.Favorites.StatusFavorite
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import mu.KLogging
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory

class FavoriteTransactionImpl : FavoriteTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val favorite = FavoriteTable

    private fun favoriteDBToFavoriteEntity(rs : ResultRow) = Favorite(
        buyerId = rs[favorite.buyerId],
        eventIdFav = rs[favorite.eventId],
        status = rs[favorite.status]
    )

    override suspend fun selectEventFromFavorite(buyerId: Long): List<Long> = dbQuery {
        logger.info("Favorite select event id by buyer id $buyerId transaction is started.")
        favorite.slice(favorite.eventId).select(favorite.buyerId eq buyerId).map{it[favorite.eventId]}
    }

    override suspend fun updateFavorite(buyerId: Long, eventId: Long, statusFav: StatusFavorite): Boolean = dbQuery {
        logger.info("Favorite $buyerId $eventId update transaction is started.")
        favorite.update({ favorite.buyerId eq buyerId;favorite.eventId eq eventId }) {
                it[status] = statusFav
        }
    } > 0

    override suspend fun selectAll(): List<Favorite>  = dbQuery{
        logger.info("Favorite select all transaction is started.")
        favorite.selectAll().map(::favoriteDBToFavoriteEntity)
    }

    override suspend fun delete(id: Pair<Long, Long>): Boolean = dbQuery {
        logger.info("Favorite ${id.first} ${id.second} delete transaction is started.")
        favorite.deleteWhere{favorite.buyerId eq id.first;favorite.eventId eq id.second}
    } > 0

    override suspend fun selectById(id: Pair<Long, Long>): Favorite? = dbQuery {
        logger.info("Favorite ${id.first} ${id.second} select by id transaction is started.")
        favorite.select{favorite.buyerId eq id.first; favorite.eventId eq id.second}
            .map(::favoriteDBToFavoriteEntity).singleOrNull()
    }
}