package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Buyer
import com.ticketEase.backend.DataClasses.DateSerializer
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTime
import com.ticketEase.backend.DataClasses.PlaceTime.PlaceTimeTable
import com.ticketEase.backend.DataClasses.PlaceTime.StatusPlaceTime
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.LocalDateTime

class PlaceTimeTransactionImpl: PlaceTimeTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val placeTime = PlaceTimeTable

    private fun placeTimeDBToPlaceTimeEntity(rs : ResultRow) = PlaceTime(
        id = rs[placeTime.placeTimeId].value,
        placeId = rs[placeTime.placeId],
        date =rs[placeTime.date],
        status = rs[placeTime.status]
    )

    override suspend fun selectByPlace(placeId: Long): List<PlaceTime>  = dbQuery{
        logger.info("PlaceTime select by place transaction is started.")
        placeTime.select(placeTime.placeId eq placeId).map(::placeTimeDBToPlaceTimeEntity)
    }

    override suspend fun updatePlaceTime(placeTimeId: Long, status: StatusPlaceTime): Boolean  = dbQuery{
        logger.info("PlaceTime update transaction is started.")
        placeTime.update({placeTime.placeTimeId eq placeTimeId}) {
            it[this.status] = status
        }
    } > 0

    override suspend fun selectIdByDate(date: Instant): List<PlaceTime>  = dbQuery{
        logger.info("PlaceTime select by date transaction is started.")
        placeTime.select(placeTime.date eq date).map(::placeTimeDBToPlaceTimeEntity)
    }

    override suspend fun selectAll(): List<PlaceTime> = dbQuery {
        logger.info("PlaceTime select all transaction is started.")
        placeTime.selectAll().map(::placeTimeDBToPlaceTimeEntity)
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        logger.info("PlaceTime delete transaction is started.")
        placeTime.deleteWhere{placeTime.placeTimeId eq id}
    } > 0

    override suspend fun selectById(id: Long): PlaceTime?  = dbQuery{
        logger.info("PlaceTime select by id transaction is started.")
        placeTime.select(placeTime.placeTimeId eq id).map(::placeTimeDBToPlaceTimeEntity).singleOrNull()
    }
}