package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.Cart
import com.example.DataClasses.CartTable
import com.ticketEase.backend.PostgreSQL.DatabaseFactory.DataBaseFactory.dbQuery
import com.ticketEase.backend.PostgreSQL.Transactions.` Adapters`.DateAdapter
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.slf4j.LoggerFactory
import java.sql.Timestamp

class CartTransactionImpl : CartTransaction {

    private val logger = LoggerFactory.getLogger(javaClass)
    private val cart = CartTable

    private fun cartDBToCartEntity(rs : ResultRow) = Cart(
        buyerId = rs[cart.buyerId],
        ticketId = rs[cart.ticketId],
        orderDate = rs[cart.orderDate]
    )

    override suspend fun createCart(buyerId: Long, ticketId: Long): Cart? = dbQuery {
        val insertStatement = cart.insert {
            it[this.buyerId] = buyerId
            it[this.ticketId] = ticketId
        }
        logger.info("Cart create transaction is started.")
        insertStatement.resultedValues?.singleOrNull()?.let(::cartDBToCartEntity)
    }

    override suspend fun updateCart(buyerId: Long, ticketId: Long, orderDate: Timestamp): Boolean = dbQuery {
        logger.info("Cart update transaction is started.")
        cart.update({ cart.buyerId eq buyerId;cart.ticketId eq ticketId }) {
                it[this.orderDate] =  DateAdapter.timestampToInstant(orderDate)
            } > 0
    }

    override suspend fun selectAll(): List<Cart>  = dbQuery{
        logger.info("Cart select all transaction is started.")
        cart.selectAll().map(::cartDBToCartEntity)
    }

    override suspend fun delete(id: Pair<Long, Long>): Boolean = dbQuery {
        logger.info("Cart delete transaction is started.")
        cart.deleteWhere{cart.buyerId eq id.first; cart.ticketId eq id.second} > 0
    }

    override suspend fun selectById(id: Pair<Long, Long>): Cart?  = dbQuery {
        logger.info("Cart select by id transaction is started.")
        cart.select{cart.buyerId eq id.first;cart.ticketId eq id.second}
            .map(::cartDBToCartEntity)
            .singleOrNull()
    }
}