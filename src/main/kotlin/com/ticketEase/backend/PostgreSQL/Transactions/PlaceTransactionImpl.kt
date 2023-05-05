package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Place
import com.example.DataClasses.PlaceTable
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory


class PlaceTransactionImpl : PlaceTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val place = PlaceTable

    private fun placeDBToPlaceEntity(rs : ResultRow) =  Place(
        id = rs[place.placeId].value,
        name = rs[place.name],
        capacity = rs[place.capacity],
        numRow = rs[place.numRow],
        numColumn = rs[place.numColumn]
    )

    override suspend fun createPlace(name: String, capacity: Long, numRow: Int?, numColumn: Int?): Place? = dbQuery {
        logger.info("Place create transaction is started.")
        val insertStatement = place.insert {
            it[place.name] = name
            it[place.capacity] = capacity
            it[place.numRow] = numRow
            it[place.numColumn] = numColumn
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::placeDBToPlaceEntity)
    }

    override suspend fun selectOneOfTypePlace(type : TypeOfPlace, row: Int?, columns: Int?): List<Place>
    = dbQuery{
        logger.info("Place select by type place transaction is started.")
        if (type == TypeOfPlace.WITH) place.select{place.numRow neq null;place.numColumn neq null}
            .map(::placeDBToPlaceEntity) else
        place.select{place.numRow eq null; place.numColumn eq null}.map(::placeDBToPlaceEntity)
    }

    override suspend fun updatePlace(placeId: Long, name: String?, capacity: Long?, numRow: Int?, numColumn: Int?): Boolean =
        dbQuery {
            //    TODO("Исключить дублирование кода")
        logger.info("Place update transaction is started.")
        val updatePlace = selectById(placeId)
        if (updatePlace != null){
            place.update({place.placeId eq placeId}){
                it[this.name] = name ?: updatePlace.name
                it[this.capacity] = capacity ?: updatePlace.capacity
                it[this.numRow] = numRow ?: updatePlace.numRow
                it[this.numColumn] = numColumn ?: updatePlace.numColumn
            }
            logger.info("Ticket update transaction is ended.")
            return@dbQuery true
        }else{
            logger.warn("Ticket isn't find")
            return@dbQuery false
        }
    }

    override suspend fun selectAll(): List<Place> = dbQuery {
        logger.info("Place select all transaction is started.")
        place.selectAll().map(::placeDBToPlaceEntity)
    }

    override suspend fun delete(id: Long): Boolean  = dbQuery{
        logger.info("Place delete transaction is started.")
        place.deleteWhere{place.placeId eq id}
    } > 0

    override suspend fun selectById(id: Long): Place? = dbQuery {
        logger.info("Place select by id transaction is started.")
        place.select(place.placeId eq id).map(::placeDBToPlaceEntity).singleOrNull()
    }
}