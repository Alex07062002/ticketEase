package com.ticketEase.backend.PostgreSQL.Transactions

import com.example.DataClasses.CartDTO
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

    private fun cartDBToCartEntity(rs : ResultRow) = CartDTO(
        buyerId = rs[cart.buyerId],
        ticketId = rs[cart.ticketId],
        orderDate = rs[cart.orderDate]
    )

    override suspend fun createCart(cartDTO: CartDTO): CartDTO? = dbQuery {
        val insertStatement = cart.insert {
            it[this.buyerId] = cartDTO.buyerId
            it[this.ticketId] = cartDTO.ticketId
        }
        logger.info("Cart create transaction is started.")
        insertStatement.resultedValues?.singleOrNull()?.let(::cartDBToCartEntity)
    }

    override suspend fun updateCart(cartDTO: CartDTO): CartDTO?{
        dbQuery {
            logger.info("Cart update transaction is started.")
            cart.update({ cart.buyerId eq cartDTO.buyerId;cart.ticketId eq cartDTO.ticketId }) {
                it[this.orderDate] = cartDTO.orderDate
            }
        }
        return selectById(Pair(cartDTO.buyerId,cartDTO.ticketId))
    }

    override suspend fun selectAll(): List<CartDTO>  = dbQuery{
        logger.info("Cart select all transaction is started.")
        cart.selectAll().map(::cartDBToCartEntity)
    }

    override suspend fun delete(id: Pair<Long, Long>): Boolean = dbQuery {
        logger.info("Cart  ${id.first} ${id.second} delete transaction is started.")
        cart.deleteWhere{cart.buyerId eq id.first; cart.ticketId eq id.second} > 0
    }

    override suspend fun selectById(id: Pair<Long, Long>): CartDTO?  = dbQuery {
        logger.info("Cart $id select by id transaction is started.")
        cart.select{cart.buyerId eq id.first;cart.ticketId eq id.second}
            .map(::cartDBToCartEntity)
            .singleOrNull()
    }
}