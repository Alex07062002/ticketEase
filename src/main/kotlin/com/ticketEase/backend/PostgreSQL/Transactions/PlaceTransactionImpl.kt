package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Cities
import com.example.DataClasses.PlaceDTO
import com.example.DataClasses.PlaceTable
import com.ticketEase.backend.DataClasses.Place.TypeOfPlace
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory


class PlaceTransactionImpl : PlaceTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val place = PlaceTable

    private fun placeDBToPlaceEntity(rs : ResultRow) =  PlaceDTO(
        id = rs[place.id].value,
        name = rs[place.name],
        capacity = rs[place.capacity],
        numRow = rs[place.numRow],
        numColumn = rs[place.numColumn],
        location = rs[place.location],
        city = rs[place.city]
    )

    override suspend fun createPlace(placeAdd : PlaceDTO) : PlaceDTO? = dbQuery{
        logger.info("Place create transaction is started.")
        val insertStatement = place.insert {
            it[place.name] = placeAdd.name
            it[place.capacity] = placeAdd.capacity
            it[place.numRow] = placeAdd.numRow
            it[place.numColumn] = placeAdd.numColumn
            it[place.location] = placeAdd.location
            it[place.city] = placeAdd.city
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::placeDBToPlaceEntity)
    }

    override suspend fun selectOneOfTypePlace(type: TypeOfPlace, city : Cities): List<PlaceDTO>
    = dbQuery{
        logger.info("Place select by type place transaction is started.")
        if (type == TypeOfPlace.WITH) place.select{place.numRow neq null;place.numColumn neq null;
            place.city eq city.toString() }.map(::placeDBToPlaceEntity) else
        place.select{place.numRow eq null; place.numColumn eq null;place.city eq city.toString()}
            .map(::placeDBToPlaceEntity)
    }

    override suspend fun updatePlace(placeUp : PlaceDTO): PlaceDTO? {
        dbQuery {
            logger.info("Place ${placeUp.id} update transaction is started.")
            place.update({ place.id eq placeUp.id }) {
                it[this.name] = placeUp.name
                it[this.capacity] = placeUp.capacity
                it[this.numRow] = placeUp.numRow
                it[this.numColumn] = placeUp.numColumn
                it[this.location] = placeUp.location
                it[this.city] = placeUp.city
            }
        }
       return placeUp.id?.let {selectById(it)}
    }

    override suspend fun selectAll(): List<PlaceDTO> = dbQuery {
        logger.info("Place select all transaction is started.")
        place.selectAll().map(::placeDBToPlaceEntity)
    }

    override suspend fun delete(id: Long): Boolean  = dbQuery{
        logger.info("Place $id delete transaction is started.")
        place.deleteWhere{place.id eq id}
    } > 0

    override suspend fun capacity(id: Long): Long  = dbQuery{
        place.slice(place.capacity).select(place.id eq id).map {it[place.capacity]}.single()
    }

    override suspend fun selectById(id: Long): PlaceDTO? = dbQuery {
        logger.info("Place $id select by id transaction is started.")
        place.select(place.id eq id).map(::placeDBToPlaceEntity).singleOrNull()
    }
}