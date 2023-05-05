package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.*
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory

class OrganizerTransactionImpl : OrganizerTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val organizer = OrganizerTable

    private fun organizerDBToOrganizerEntity(rs : ResultRow) = Organizer(
        id = rs[organizer.OrganizerId].value,
        name = rs[organizer.name],
        surname = rs[organizer.surname],
        login = rs[organizer.login],
        password = rs[organizer.password], // TODO Maybe change because use JWTToken
        email = rs[organizer.email],
        mobile = rs[organizer.mobile],
        city = rs[organizer.city],
        status = rs[organizer.status]
    )

    override suspend fun updateCityPerson(id : Long, city: Cities): Boolean = dbQuery {
        logger.info("Organizer update city transaction is started.")
        organizer.update ({organizer.OrganizerId eq id}){
            it[this.city] = city
        }
    } > 0

    override suspend fun updateParamsOrganizer(
        organizerId : Long,
        name: String?,
        surname: String?,
        email: String?,
        mobile: String?,
        status : StatusOrganizer?
    ): Boolean = dbQuery{
        logger.info("Organizer update transaction is started.")
        val organizerUpdate = selectById(organizerId)
        if (organizerUpdate != null) {
            organizer.update({ organizer.OrganizerId eq organizerId}) {
                it[this.name] = name ?: organizerUpdate.name
                it[this.surname] = surname ?: organizerUpdate.surname
                it[this.email] = email ?: organizerUpdate.email
                it[this.mobile] = mobile ?: organizerUpdate.mobile
                it[this.status] = status ?: organizerUpdate.status
            }
            logger.info("Buyer update transaction is ended.")
            return@dbQuery true
        }
        else{
            logger.warn("Update buyer isn't find.")
            return@dbQuery false
        }
    }

    override suspend fun createOrganizer(
        name: String,
        surname: String,
        login: String,
        password: String,
        email: String,
        mobile: String?,
        city: Cities,
        status: StatusOrganizer
    ) : Organizer?  = dbQuery{
        logger.info("Organizer create transaction is started.")
        val insertStatement = organizer.insert {
           it[organizer.name] = name
           it[organizer.surname] = surname
           it[organizer.login] = login
           it[organizer.password] =password
           it[organizer.email] = email
           it[organizer.mobile] = mobile
            it[organizer.city] = city
           it[organizer.status] = status
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::organizerDBToOrganizerEntity)
    }

    override suspend fun selectOrganizerByCity(city : Cities): Query = dbQuery{
        logger.info("Organizer select organizer id by city is started.")
        organizer.slice(organizer.OrganizerId).select(organizer.city eq city)
    }

    override suspend fun selectAll(): List<Organizer>  = dbQuery{
        logger.info("Organizer select all transaction is started.")
        organizer.selectAll().map(::organizerDBToOrganizerEntity)
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        logger.info("Organizer delete transaction is started.")
        organizer.deleteWhere{organizer.OrganizerId eq id}
    } > 0

    override suspend fun selectById(id: Long): Organizer?  = dbQuery{
        logger.info("Organizer select by id transaction is started.")
        organizer.select(organizer.OrganizerId eq id).map(::organizerDBToOrganizerEntity).singleOrNull()
    }
}