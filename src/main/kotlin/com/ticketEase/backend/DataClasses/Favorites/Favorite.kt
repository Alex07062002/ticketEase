package com.example.DataClasses.Favorites

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Person.BuyerTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table



@Serializable
data class Favorite(val buyerId : Long,
                    val eventIdFav : Long,
                    val status : StatusFavorite)

object FavoriteTable : Table("favorites"){
    val buyerId = long("buyer_id").references(BuyerTable.buyerId)
    val eventId = long("event_id").references(EventTable.eventId)
    val status = enumeration("status",StatusFavorite::class)
    override val primaryKey = PrimaryKey(buyerId, eventId,name = "FavoritePK")
}
