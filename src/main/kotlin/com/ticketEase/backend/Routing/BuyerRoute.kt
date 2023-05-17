package com.ticketEase.backend.Routing

import com.example.DataClasses.Person.*
import com.ticketEase.backend.Auth.Hashing.Hash
import com.ticketEase.backend.Auth.Hashing.HashServiceImpl
import com.ticketEase.backend.Auth.token.JwtTokenService
import com.ticketEase.backend.Auth.token.TokenClaim
import com.ticketEase.backend.Auth.token.TokenConfig
import com.ticketEase.backend.PostgreSQL.Transactions.BuyerTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.codec.digest.DigestUtils

@Suppress("unused")
fun Route.buyerRoute(tokenConfig: TokenConfig){
    val buyerService = BuyerTransactionImpl()
    val tokenService = JwtTokenService()
    val hashService = HashServiceImpl()

    route("/buyers"){
        post{
            call.respond(HttpStatusCode.OK,buyerService.selectAll())
        }
        delete("/{id}") {
            val idFromQuery = call.parameters["id"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid organizer id")
            }
            buyerService.delete(idFromQuery.toLong())
            call.respond("Buyer is deleted.")
        }
        post("/create"){
            val parameters = call.receive<Buyer>()
            val buyer = buyerService.createBuyer(parameters)
            if (buyer == null) call.respond(HttpStatusCode.BadRequest,"Buyer isn't created.") else
                call.respond(
                    HttpStatusCode.OK,BuyerResponse(tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = parameters.id.toString())
                    )))
        }
        put("/{id}/update"){
            val parameters = call.receive<Buyer>()
            val buyer = buyerService.updateParamsBuyer(parameters)
            if (buyer == null) call.respond(HttpStatusCode.BadRequest,"Buyer isn't updated.") else
                call.respond(
                    HttpStatusCode.OK,BuyerResponse(buyer.password))
        }
        put("/signIn") {
            val parameters = call.receive<BuyerRequest>()
            val buyer = buyerService.selectByLogin(parameters.login)
            if (buyer?.secret == null) call.respond(HttpStatusCode.Conflict, "Invalid parameters.") else {
                val isValidPassword = hashService.verify(
                    value = parameters.password,
                    saltedHash = Hash(
                        buyer.password,
                        buyer.secret
                    )
                )
                if (!isValidPassword) {
                    println("Entered hash: ${DigestUtils.sha256Hex("${buyer.secret}${parameters.password}")}, Hashed PW: ${buyer.password}")
                    call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
                }

                call.respond(
                    status = HttpStatusCode.OK,
                    message = BuyerResponse(
                        token = buyer.password
                    )
                )
            }
        }
        post("/{login}"){
            val loginFromQuery = call.parameters["login"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid organizer id")
            }
            val response = buyerService.selectByLogin(loginFromQuery)
            if (response == null) call.respond(HttpStatusCode.OK, "Login not found") else
                call.respond(HttpStatusCode.Conflict, "Login is created earlier")
        }
        post("/{id}/{city}"){
            val idFromQuery = call.parameters["id"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid organizer id")
            }
            val cityFromQuery = call.parameters["city"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid city")
            }
            val result = buyerService.updateCityPerson(idFromQuery.toLong(), Cities.valueOf(cityFromQuery))
            if(result) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.BadRequest)
        }
    }
}