package com.ticketEase.backend.Routing

import com.example.DataClasses.Person.Cities
import com.example.DataClasses.Person.Organizer
import com.example.DataClasses.Person.OrganizerRequest
import com.example.DataClasses.Person.OrganizerResponse
import com.ticketEase.backend.Auth.Hashing.Hash
import com.ticketEase.backend.Auth.Hashing.HashServiceImpl
import com.ticketEase.backend.Auth.token.JwtTokenService
import com.ticketEase.backend.Auth.token.TokenClaim
import com.ticketEase.backend.Auth.token.TokenConfig
import com.ticketEase.backend.PostgreSQL.Transactions.OrganizerTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.apache.commons.codec.digest.DigestUtils

@Suppress("unused")
fun Route.organizerRoute(tokenConfig: TokenConfig){
    val organizerService = OrganizerTransactionImpl()
    val tokenService = JwtTokenService()
    val hashService = HashServiceImpl()

    route("/organizers"){
        post{
            call.respond(HttpStatusCode.OK,organizerService.selectAll())
        }
        delete("/{id}") {
            val idFromQuery = call.parameters["id"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid organizer id")
            }
            organizerService.delete(idFromQuery.toLong())
            call.respond("Organizer is deleted.")
        }
        post("/create"){
            val parameters = call.receive<Organizer>()
            val organizer = organizerService.createOrganizer(parameters)
            if (organizer == null) call.respond(HttpStatusCode.BadRequest,"Organizer isn't created.") else
                call.respond(HttpStatusCode.OK,tokenService.generate(
                        config = tokenConfig,
                TokenClaim(
                name = "userId",
                value = parameters.id.toString())))
        }
        put("/{id}/update"){
            val parameters = call.receive<Organizer>()
            val organizer = organizerService.updateParamsOrganizer(parameters)
            if (organizer == null) call.respond(HttpStatusCode.BadRequest,"Organizer isn't updated.") else
                call.respond(HttpStatusCode.OK,tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = parameters.id.toString())))
        }
        put("/signIn") {
            val parameters = call.receive<OrganizerRequest>()
            val organizer = organizerService.selectByLogin(parameters.login)
            if (organizer == null) call.respond(HttpStatusCode.Conflict, "Invalid parameters.") else {
                val isValidPassword = hashService.verify(
                    value = parameters.password,
                    saltedHash = Hash(
                        organizer.password,
                        organizer.secret
                    )
                )
                if (!isValidPassword) {
                    println("Entered hash: ${DigestUtils.sha256Hex("${organizer.secret}${parameters.password}")}, Hashed PW: ${organizer.password}")
                    call.respond(HttpStatusCode.Conflict, "Incorrect username or password")
                }
                val token = tokenService.generate(
                    config = tokenConfig,
                    TokenClaim(
                        name = "userId",
                        value = organizer.id.toString()
                    )
                )

                call.respond(
                    status = HttpStatusCode.OK,
                    message = OrganizerResponse(
                        token = token
                    )
                )
            }
        }
        post("/{city}"){
            val cityFromQuery = call.parameters["city"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid city")
            }
            organizerService.selectOrganizerByCity(Cities.valueOf(cityFromQuery))
        }
        post("/{id}/{city}"){
            val idFromQuery = call.parameters["id"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid organizer id")
            }
            val cityFromQuery = call.parameters["city"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid city")
            }
             val result = organizerService.updateCityPerson(idFromQuery.toLong(),Cities.valueOf(cityFromQuery))
            if(result) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.BadRequest)
        }
        }
    }
