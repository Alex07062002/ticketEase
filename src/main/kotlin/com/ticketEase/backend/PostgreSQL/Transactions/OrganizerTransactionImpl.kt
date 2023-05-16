package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.*
import com.ticketEase.backend.Auth.Hashing.HashServiceImpl
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory

class OrganizerTransactionImpl : OrganizerTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val organizer = OrganizerTable
    private val hashing = HashServiceImpl()

    private fun organizerDBToOrganizerEntity(rs : ResultRow) = Organizer(
        id = rs[organizer.id].value,
        name = rs[organizer.name],
        surname = rs[organizer.surname],
        login = rs[organizer.login],
        password = rs[organizer.password],
        email = rs[organizer.email],
        mobile = rs[organizer.mobile],
        city = rs[organizer.city],
        status = rs[organizer.status],
        secret = rs[organizer.secret]

    )

    override suspend fun updateCityPerson(id : Long, city: Cities): Boolean = dbQuery {
        logger.info("Organizer $id update city to $city transaction is started.")
        organizer.update ({organizer.id eq id}){
            it[this.city] = city
        }
    } > 0

    override suspend fun selectByLogin(login: String): Organizer?  = dbQuery{
        organizer.select(organizer.login eq login).map(::organizerDBToOrganizerEntity).singleOrNull()
    }

    override suspend fun updateParamsOrganizer(organizerUp: Organizer): Organizer? {
        dbQuery {
            logger.info("Organizer ${organizerUp.id} update transaction is started.")
            organizer.update({ organizer.id eq organizerUp.id }) {
                it[this.name] = organizerUp.name
                it[this.surname] = organizerUp.surname
                it[this.email] = organizerUp.email
                it[this.mobile] = organizerUp.mobile
                it[this.status] = organizerUp.status
            }
        }
        return selectById(organizerUp.id)
    }
    override suspend fun createOrganizer(organizerCreate: Organizer) : Organizer?  = dbQuery{
        logger.info("Organizer create transaction is started.")
        val pswdHash = hashing.generateSaltedHash(organizerCreate.password)
        val insertStatement = organizer.insert {
           it[organizer.name] = organizerCreate.name
           it[organizer.surname] = organizerCreate.surname
           it[organizer.login] = organizerCreate.login
           it[organizer.password] = pswdHash.hash
           it[organizer.email] = organizerCreate.email
           it[organizer.mobile] = organizerCreate.mobile
            it[organizer.city] = organizerCreate.city
           it[organizer.status] = organizerCreate.status
            it[organizer.secret] = pswdHash.secret
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::organizerDBToOrganizerEntity)
    }

    override suspend fun selectOrganizerByCity(city : Cities): Query = dbQuery{
        logger.info("Organizer select organizer id by city $city is started.")
        organizer.slice(organizer.id).select(organizer.city eq city)
    }

    override suspend fun selectAll(): List<Organizer>  = dbQuery{
        logger.info("Organizer select all transaction is started.")
        organizer.selectAll().map(::organizerDBToOrganizerEntity)
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        logger.info("Organizer $id delete transaction is started.")
        organizer.deleteWhere{organizer.id eq id}
    } > 0

    override suspend fun selectById(id: Long): Organizer?  = dbQuery{
        organizer.select {organizer.id eq id}.map(::organizerDBToOrganizerEntity).singleOrNull()
    }
}