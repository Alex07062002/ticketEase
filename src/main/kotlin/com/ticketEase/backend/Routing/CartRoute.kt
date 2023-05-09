package com.ticketEase.backend.Routing

import io.ktor.server.routing.*

/**
 *     override suspend fun createCart(buyerId: Long, ticketId: Long): Cart? = dbQuery {
 *         val insertStatement = cart.insert {
 *             it[this.buyerId] = buyerId
 *             it[this.ticketId] = ticketId
 *         }
 *         logger.info("Cart create transaction is started.")
 *         insertStatement.resultedValues?.singleOrNull()?.let(::cartDBToCartEntity)
 *     }
 *
 *     override suspend fun updateCart(buyerId: Long, ticketId: Long, orderDate: Timestamp): Boolean = dbQuery {
 *         logger.info("Cart $buyerId $ticketId update transaction is started.")
 *         cart.update({ cart.buyerId eq buyerId;cart.ticketId eq ticketId }) {
 *                 it[this.orderDate] =  DateAdapter.timestampToInstant(orderDate)
 *             } > 0
 *     }
 *
 *     override suspend fun selectAll(): List<Cart>  = dbQuery{
 *         logger.info("Cart select all transaction is started.")
 *         cart.selectAll().map(::cartDBToCartEntity)
 *     }
 *
 *     override suspend fun delete(id: Pair<Long, Long>): Boolean = dbQuery {
 *         logger.info("Cart  ${id.first} ${id.second} delete transaction is started.")
 *         cart.deleteWhere{cart.buyerId eq id.first; cart.ticketId eq id.second} > 0
 *     }
 *
 *     override suspend fun selectById(id: Pair<Long, Long>): Cart?  = dbQuery {
 *         logger.info("Cart $id select by id transaction is started.")
 *         cart.select{cart.buyerId eq id.first;cart.ticketId eq id.second}
 *             .map(::cartDBToCartEntity)
 *             .singleOrNull()
 *     }
 */
@Suppress("unused")
fun Route.cartRoute(){

}