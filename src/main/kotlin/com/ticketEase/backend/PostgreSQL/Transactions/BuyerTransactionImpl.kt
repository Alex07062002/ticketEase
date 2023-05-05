package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Person.Buyer
import com.example.DataClasses.Person.BuyerTable
import com.example.DataClasses.Person.Cities
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory

class BuyerTransactionImpl : BuyerTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val buyer = BuyerTable

    private fun buyerDBToBuyerEntity(rs : ResultRow) = Buyer(
        id = rs[buyer.buyerId].value,
        name = rs[buyer.name],
        surname = rs[buyer.surname],
        login = rs[buyer.login],
        password = rs[buyer.password], // TODO Maybe change because use JWTToken
        email = rs[buyer.email],
        mobile = rs[buyer.mobile],
        city = rs[buyer.city]
    )

    override suspend fun updateCityPerson(id : Long, city: Cities): Boolean = dbQuery{
        logger.info("Buyer update city transaction is started.")
        buyer.update ({buyer.buyerId eq id}){
            it[this.city] = city
        } > 0
    }

    override suspend fun updateParamsBuyer(
        buyerId : Long,
        name: String?,
        surname: String?,
        email: String?,
        mobile: String?
    ): Boolean  = dbQuery {
        logger.info("Buyer update transaction is started.")
        val updateBuyer = selectById(buyerId)
        if (updateBuyer != null) {
            buyer.update({ buyer.buyerId eq buyerId}) {
                it[this.name] = name ?: updateBuyer.name
                it[this.surname] = surname ?: updateBuyer.surname
                it[this.email] = email ?: updateBuyer.email
                it[this.mobile] = mobile ?: updateBuyer.mobile
            }
            logger.info("Buyer update transaction is ended.")
            return@dbQuery true
        }
        else{
            logger.warn("Update buyer isn't find.")
            return@dbQuery false
        }
    }

    override suspend fun createBuyer(
        name: String,
        surname: String,
        login: String,
        password: String,
        email: String,
        mobile: String?,
        city: Cities
    ): Buyer = dbQuery {
        logger.info("Buyer create transaction is started.")
        val id = buyer.insertAndGetId {
            it[buyer.name] = name
            it[buyer.surname] = surname
            it[buyer.login] = login
            it[buyer.password] = password
            it[buyer.mobile] = mobile
            it[buyer.city] = city
        }
        Buyer(id.value,name,surname,login,password,email,mobile,city)
    }

    override suspend fun selectAll(): List<Buyer> = dbQuery {
        logger.info("Buyer select all transaction is started.")
        buyer.selectAll().map(::buyerDBToBuyerEntity)
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        logger.info("Buyer delete transaction is started.")
        buyer.deleteWhere {buyer.buyerId eq id}
    } > 0

    override suspend fun selectById(id: Long): Buyer? = dbQuery {
        logger.info("Buyer select by id transaction is started.")
        buyer.select{buyer.buyerId eq id}.map(::buyerDBToBuyerEntity).singleOrNull()
    }
}