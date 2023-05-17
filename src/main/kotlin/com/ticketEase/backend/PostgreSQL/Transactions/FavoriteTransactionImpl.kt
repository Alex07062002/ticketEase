package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Favorites.FavoriteDTO
import com.example.DataClasses.Favorites.FavoriteTable
import com.example.DataClasses.Favorites.StatusFavorite
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory

class FavoriteTransactionImpl : FavoriteTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val favorite = FavoriteTable

    private fun favoriteDBToFavoriteEntity(rs : ResultRow) = FavoriteDTO(
        buyerId = rs[favorite.buyerId],
        eventId = rs[favorite.eventId],
        status = StatusFavorite.valueOf(rs[favorite.status])
    )

    override suspend fun createFavorite(favoriteDTO: FavoriteDTO): FavoriteDTO?  = dbQuery{
        val insertStatement = favorite.insert {
            it[this.buyerId] = favoriteDTO.buyerId
            it[this.eventId] = favoriteDTO.eventId
        }
        logger.info("Favorite create transaction is started.")
        insertStatement.resultedValues?.singleOrNull()?.let(::favoriteDBToFavoriteEntity)
        }

    override suspend fun selectEventFromFavorite(buyerId: Long): List<Long> = dbQuery {
        logger.info("Favorite select event id by buyer id $buyerId transaction is started.")
        favorite.slice(favorite.eventId).select(favorite.buyerId eq buyerId).map{it[favorite.eventId]}
    }

    override suspend fun updateFavorite(favoriteDTO: FavoriteDTO): FavoriteDTO? {
        dbQuery {
            logger.info("Favorite update transaction is started.")
            favorite.update({ favorite.buyerId eq favoriteDTO.buyerId;favorite.eventId eq favoriteDTO.eventId }) {
                it[status] = favoriteDTO.status.toString()
            }
        }
        return selectById(Pair(favoriteDTO.buyerId,favoriteDTO.eventId))
    }

    override suspend fun selectAll(): List<FavoriteDTO>  = dbQuery{
        logger.info("Favorite select all transaction is started.")
        favorite.selectAll().map(::favoriteDBToFavoriteEntity)
    }

    override suspend fun delete(id: Pair<Long, Long>): Boolean = dbQuery {
        logger.info("Favorite ${id.first} ${id.second} delete transaction is started.")
        favorite.deleteWhere{favorite.buyerId eq id.first;favorite.eventId eq id.second}
    } > 0

    override suspend fun selectById(id: Pair<Long, Long>): FavoriteDTO? = dbQuery {
        logger.info("Favorite ${id.first} ${id.second} select by id transaction is started.")
        favorite.select{favorite.buyerId eq id.first; favorite.eventId eq id.second}
            .map(::favoriteDBToFavoriteEntity).singleOrNull()
    }
}