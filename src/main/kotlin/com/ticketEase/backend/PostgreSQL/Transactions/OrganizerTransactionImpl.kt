package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.*
import com.ticketEase.backend.Auth.Hashing.HashServiceImpl
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory

class OrganizerTransactionImpl : OrganizerTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    val organizer = OrganizerTable
    private val hashing = HashServiceImpl()

    private fun organizerDBToOrganizerEntity(rs : ResultRow) = Organizer(
        id = rs[organizer.id].value,
        name = rs[organizer.name],
        surname = rs[organizer.surname],
        login = rs[organizer.login],
        password = rs[organizer.password],
        email = rs[organizer.email],
        mobile = rs[organizer.mobile],
        city = Cities.valueOf(rs[organizer.city]),
        status = StatusOrganizer.valueOf(rs[organizer.status]),
        secret = rs[organizer.secret]
    )

    private fun organizerDBToOrganizerWithoutPswd(rs : ResultRow) = OrganizerWithoutPswd(
        id = rs[organizer.id].value,
        name = rs[organizer.name],
        surname = rs[organizer.surname],
        token = rs[organizer.password],
        email = rs[organizer.email],
        mobile = rs[organizer.mobile],
        status = StatusOrganizer.valueOf(rs[organizer.status])
    )

    override suspend fun updateCityPerson(token : String, city: Cities): Boolean = dbQuery {
        logger.info("Organizer update city to $city transaction is started.")
        organizer.update ({organizer.password eq token}){
            it[this.city] = city.toString()
        }
    } > 0

    override suspend fun selectByLogin(login: String): Organizer?  = dbQuery{
        organizer.select(organizer.login eq login).map(::organizerDBToOrganizerEntity).singleOrNull()
    }

    override suspend fun selectByToken(token: String): OrganizerWithoutPswd?  = dbQuery{
        organizer.slice(organizer.id,organizer.name,organizer.surname,organizer.email,
            organizer.mobile,organizer.status,organizer.password).select(organizer.password eq token)
            .map(::organizerDBToOrganizerWithoutPswd).singleOrNull()
    }

    override suspend fun updateParamsOrganizer(organizerUp: OrganizerWithoutPswd): OrganizerWithoutPswd? {
        dbQuery {
            logger.info("Organizer update transaction is started.")
            organizer.update({ organizer.password eq organizerUp.token }) {
                it[this.name] = organizerUp.name
                it[this.surname] = organizerUp.surname
                it[this.email] = organizerUp.email
                it[this.mobile] = organizerUp.mobile
                it[this.status] = organizerUp.status.toString()
            }
        }
        return selectByToken(organizerUp.token)
    }
    override suspend fun createOrganizer(organizerCreate: Organizer) : OrganizerWithoutPswd?  = dbQuery{
        logger.info("Organizer create transaction is started.")
        val pswdHash = hashing.generateSaltedHash(organizerCreate.password)
        val insertStatement = organizer.insert {
           it[organizer.name] = organizerCreate.name
           it[organizer.surname] = organizerCreate.surname
           it[organizer.login] = organizerCreate.login
           it[organizer.password] = pswdHash.hash
           it[organizer.email] = organizerCreate.email
           it[organizer.mobile] = organizerCreate.mobile
            it[organizer.city] = organizerCreate.city.toString()
           it[organizer.status] = organizerCreate.status.toString()
            it[organizer.secret] = pswdHash.secret
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::organizerDBToOrganizerWithoutPswd)
    }

    override suspend fun selectOrganizerByCity(city : Cities): List<Long> = dbQuery{
        logger.info("Organizer select organizer id by city $city is started.")
        organizer.slice(organizer.id).select(organizer.city eq city.toString()).map{it[organizer.id].value}
    }

    override suspend fun selectAll(): List<Organizer>  = dbQuery{
        logger.info("Organizer select all transaction is started.")
        organizer.selectAll().map(::organizerDBToOrganizerEntity)
    }

    override suspend fun delete(token: String): Boolean = dbQuery {
        logger.info("Organizer delete transaction is started.")
        organizer.deleteWhere{organizer.password eq token}
    } > 0

    @Deprecated("Change to selectByToken")
    // Warning without security
    override suspend fun selectById(id: Long): Organizer?  = dbQuery{
        organizer.select {organizer.id eq id}.map(::organizerDBToOrganizerEntity).singleOrNull()
    }
}