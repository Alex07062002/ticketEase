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
        city = rs[buyer.city],
        secret = rs[buyer.secret]
    )

    override suspend fun updateCityPerson(id : Long, city: Cities): Boolean = dbQuery{
        logger.info("Buyer $id update city to $city transaction is started.")
        buyer.update ({buyer.id eq id}){
            it[this.city] = city
        } > 0
    }

    override suspend fun selectByLogin(login: String): Buyer?  = dbQuery{
        buyer.select(buyer.login eq login).map(::buyerDBToBuyerEntity).singleOrNull()
    }

    override suspend fun updateParamsBuyer(buyerUp: Buyer): Buyer?{
        dbQuery {
            logger.info("Buyer update transaction is started.")
            buyer.update({ buyer.id eq buyerUp.id }) {
                it[this.name] = buyerUp.name
                it[this.surname] = buyerUp.surname
                it[this.email] = buyerUp.email
                it[this.mobile] = buyerUp.mobile
            }
        }
        return selectById(buyerUp.id)
    }

    override suspend fun createBuyer(buyerCreate: Buyer): Buyer? = dbQuery {
        logger.info("Buyer create transaction is started.")
        val pswdHash =  hashing.generateSaltedHash(buyerCreate.password)
        val insertStatement = buyer.insert {
            it[buyer.name] = buyerCreate.name
            it[buyer.surname] = buyerCreate.surname
            it[buyer.login] = buyerCreate.login
            it[buyer.password] = pswdHash.hash
            it[buyer.mobile] = buyerCreate.mobile
            it[buyer.city] = buyerCreate.city
            it[buyer.secret] = pswdHash.secret
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::buyerDBToBuyerEntity)
    }



    override suspend fun selectAll(): List<Buyer> = dbQuery {
        logger.info("Buyer select all transaction is started.")
        buyer.selectAll().map(::buyerDBToBuyerEntity)
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        logger.info("Buyer $id delete transaction is started.")
        buyer.deleteWhere {buyer.id eq id}
    } > 0

    override suspend fun selectById(id: Long): Buyer? = dbQuery {
       buyer.select {buyer.id eq id}.map(::buyerDBToBuyerEntity).singleOrNull()
    }
}