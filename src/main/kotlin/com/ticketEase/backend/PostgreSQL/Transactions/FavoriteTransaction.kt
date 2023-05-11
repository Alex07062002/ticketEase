package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Favorites.FavoriteDTO
import com.example.DataClasses.Favorites.StatusFavorite

interface FavoriteTransaction : CRUDOperations<FavoriteDTO,Pair<Long,Long>> {

    suspend fun createFavorite(favorite: FavoriteDTO) : FavoriteDTO?
    suspend fun selectEventFromFavorite(buyerId: Long) : List<Long>

    suspend fun updateFavorite(favorite: FavoriteDTO) : FavoriteDTO?

}