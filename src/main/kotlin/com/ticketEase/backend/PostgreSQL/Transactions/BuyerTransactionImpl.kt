package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.*
import com.ticketEase.backend.Auth.Hashing.HashServiceImpl
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory


class BuyerTransactionImpl : BuyerTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val buyer = BuyerTable
    private val hashing = HashServiceImpl()

    private fun buyerDBToBuyerEntity(rs : ResultRow) = Buyer(
        id = rs[buyer.id].value,
        name = rs[buyer.name],
        surname = rs[buyer.surname],
        login = rs[buyer.login],
        password = rs[buyer.password],
        email = rs[buyer.email],
        mobile = rs[buyer.mobile],
        city = Cities.valueOf(rs[buyer.city]),
        secret = rs[buyer.secret]
    )

    private fun buyerDBToBuyerWithoutPswd(rs : ResultRow) = BuyerWithoutPswd(
        id = rs[buyer.id].value,
        name = rs[buyer.name],
        surname = rs[buyer.surname],
        email = rs[buyer.email],
        mobile = rs[buyer.mobile],
        token = rs[buyer.password]
    )

    override suspend fun updateCityPerson(token : String, city: Cities): Boolean = dbQuery{
        logger.info("Buyer update city to $city transaction is started.")
        buyer.update ({buyer.password eq token}){
            it[this.city] = city.toString()
        } > 0
    }
    override suspend fun selectByLogin(login: String): Buyer?  = dbQuery{
        buyer.select(buyer.login eq login).map(::buyerDBToBuyerEntity).singleOrNull()
    }

    override suspend fun checkByLogin(login: String): Boolean = dbQuery {
        buyer.select(buyer.login eq login).count()
    } > 0

    override suspend fun selectByToken(token: String): BuyerWithoutPswd? = dbQuery {
        buyer.slice(buyer.id,buyer.name,buyer.surname,buyer.email,buyer.mobile,buyer.password)
            .select(buyer.password eq token).map(::buyerDBToBuyerWithoutPswd).singleOrNull()
    }

    override suspend fun selectIdByToken(token: String): Long = dbQuery {
        buyer.slice(buyer.id).select(buyer.password eq token).map {it[buyer.id].value}.single()
    }

    override suspend fun updateParamsBuyer(buyerUp: BuyerWithoutPswd): BuyerWithoutPswd? {
        dbQuery {
            logger.info("Buyer update transaction is started.")
            buyer.update({ buyer.password eq buyerUp.token}) {
                it[this.name] = buyerUp.name
                it[this.surname] = buyerUp.surname
                it[this.email] = buyerUp.email
                it[this.mobile] = buyerUp.mobile
            }
        }
            return selectByToken(buyerUp.token)
    }

    override suspend fun createBuyer(buyerCreate: Buyer): BuyerWithoutPswd? = dbQuery {
        logger.info("Buyer create transaction is started.")
        val pswdHash =  hashing.generateSaltedHash(buyerCreate.password)
        val insertStatement = buyer.insert {
            it[buyer.name] = buyerCreate.name
            it[buyer.surname] = buyerCreate.surname
            it[buyer.login] = buyerCreate.login
            it[buyer.email] = buyerCreate.email
            it[buyer.password] = pswdHash.hash
            it[buyer.mobile] = buyerCreate.mobile
            it[buyer.city] = buyerCreate.city.toString()
            it[buyer.secret] = pswdHash.secret
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::buyerDBToBuyerWithoutPswd)
    }

    override suspend fun selectAll(): List<Buyer> = dbQuery {
        logger.info("Buyer select all transaction is started.")
        buyer.selectAll().map(::buyerDBToBuyerEntity)
    }

    override suspend fun delete(token: String): Boolean = dbQuery {
        logger.info("Buyer delete transaction is started.")
        buyer.deleteWhere {buyer.password eq token}
    } > 0

    @Deprecated("Change to selectByToken")
    // Warning without security
    override suspend fun selectById(id: Long): Buyer? = dbQuery {
       buyer.select {buyer.id eq id}.map(::buyerDBToBuyerEntity).singleOrNull()
    }
}