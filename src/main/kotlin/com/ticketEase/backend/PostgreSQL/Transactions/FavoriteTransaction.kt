package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Favorites.Favorite
import com.example.DataClasses.Favorites.StatusFavorite

interface FavoriteTransaction : CRUDOperations<Favorite,Pair<Long,Long>> {

    suspend fun selectEventFromFavorite() : List<Long>

    suspend fun updateFavorite(buyerId : Long, eventId : Long, status : StatusFavorite) : Boolean

}