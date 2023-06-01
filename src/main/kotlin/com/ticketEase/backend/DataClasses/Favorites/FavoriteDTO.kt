package com.example.DataClasses.Favorites

import com.example.DataClasses.Event.EventTable
import com.example.DataClasses.Person.BuyerTable
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table


@Serializable
data class FavoriteDTO(val buyerId : Long,
                       val eventId : Long,
                       val status : StatusFavorite = StatusFavorite.IS)

@Serializable
data class FavoriteWithoutStatus(val buyerId : Long, val eventId : Long)


object FavoriteTable : Table("favorites"){
    val buyerId = long("buyer_id").references(BuyerTable.id)
    val eventId = long("event_id").references(EventTable.id)
    val status = varchar("status",30)
    override val primaryKey = PrimaryKey(buyerId, eventId,name = "FavoritePK")
}
