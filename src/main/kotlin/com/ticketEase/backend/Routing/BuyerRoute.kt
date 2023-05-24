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
        delete("/delete") {
            val parameters = call.receive<BuyerResponse>()
            buyerService.delete(parameters.token)
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
        post("/token"){
            val parameters = call.receive<BuyerResponse>()
            val buyer = buyerService.selectByLogin(parameters.token)
            if (buyer == null) call.respond(HttpStatusCode.BadRequest,"Buyer isn't found.") else
                call.respond(HttpStatusCode.OK,buyer)
        }
        put("/{id}/update"){
            val parameters = call.receive<BuyerWithoutPswd>()
            val buyer = buyerService.updateParamsBuyer(parameters)
            if (buyer == null) call.respond(HttpStatusCode.BadRequest,"Buyer isn't updated.") else
                call.respond(
                    HttpStatusCode.OK,buyer)
        }
        post("/signIn") {
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
        post("/{login}/check"){
            val loginFromQuery = call.parameters["login"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid organizer id")
            }
            val response = buyerService.checkByLogin(loginFromQuery)
                call.respond(HttpStatusCode.OK, response)
        }
        post("/updateCity"){
            val parameters = call.receive<BuyerUpdateCity>()
            val result = buyerService.updateCityPerson(parameters.token, parameters.city)
            if(result) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.BadRequest)
        }
    }
}