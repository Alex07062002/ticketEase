package com.ticketEase.backend.PostgreSQL.Transactions

import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTime
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import com.ticketEase.backend.DataClasses.PlaceTime.StatusPlaceTime
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory
import java.time.Instant

class PlaceTimeTransactionImpl: PlaceTimeTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val placeTime = PlaceTimeTable

    private fun placeTimeDBToPlaceTimeEntity(rs : ResultRow) = PlaceTime(
        id = rs[placeTime.id].value,
        placeId = rs[placeTime.placeId],
        date =rs[placeTime.date],
        status = rs[placeTime.status]
    )

    override suspend fun createPlaceTime(placeId: Long, date: Instant, status: StatusPlaceTime): PlaceTime?  = dbQuery{
        logger.info("PlaceTime create transaction is started.")
        val insertStatement = placeTime.insert {
            it[placeTime.placeId] = placeId
            it[placeTime.date] = date
            it[placeTime.status] = status
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::placeTimeDBToPlaceTimeEntity)
    }


    override suspend fun selectByPlace(placeId: Long): List<PlaceTime>  = dbQuery{
        logger.info("PlaceTime select by place $placeId transaction is started.")
        placeTime.select(placeTime.placeId eq placeId).map(::placeTimeDBToPlaceTimeEntity)
    }

    override suspend fun updatePlaceTime(placeTimeId: Long, status: StatusPlaceTime): Boolean  = dbQuery{
        logger.info("PlaceTime $placeTimeId update transaction is started.")
        placeTime.update({placeTime.id eq placeTimeId}) {
            it[this.status] = status
        }
    } > 0

    override suspend fun selectIdByDate(date: Instant): List<PlaceTime>  = dbQuery{
        logger.info("PlaceTime select by date $date transaction is started.")
        placeTime.select(placeTime.date eq date).map(::placeTimeDBToPlaceTimeEntity)
    }

    override suspend fun selectDateById(placeTimeId: Long): Instant?  = dbQuery{
        logger.info("PlaceTime select date by id $placeTimeId transaction is started.")
        placeTime.slice(placeTime.date).select(placeTime.id eq placeTimeId).map{it[placeTime.date]}.singleOrNull()
    }

    override suspend fun selectAll(): List<PlaceTime> = dbQuery {
        logger.info("PlaceTime select all transaction is started.")
        placeTime.selectAll().map(::placeTimeDBToPlaceTimeEntity)
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        logger.info("PlaceTime $id delete transaction is started.")
        placeTime.deleteWhere{placeTime.id eq id}
    } > 0

    override suspend fun selectById(id: Long): PlaceTime?  = dbQuery{
        logger.info("PlaceTime $id select by id transaction is started.")
        placeTime.select(placeTime.id eq id).map(::placeTimeDBToPlaceTimeEntity).singleOrNull()
    }
}