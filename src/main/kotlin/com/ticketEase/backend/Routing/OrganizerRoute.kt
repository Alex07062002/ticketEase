package com.ticketEase.backend.Routing

import com.example.DataClasses.Person.*
import com.ticketEase.backend.Auth.Hashing.Hash
import com.ticketEase.backend.Auth.Hashing.HashServiceImpl
import com.ticketEase.backend.Auth.token.JwtTokenService
import com.ticketEase.backend.Auth.token.TokenClaim
import com.ticketEase.backend.Auth.token.TokenConfig
import com.ticketEase.backend.PostgreSQL.Transactions.OrganizerTransactionImpl
import io.ktor.http.*
import io.ktor.server.application.*
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
        delete("/delete") {
            val parameters = call.receive<OrganizerResponse>()
            organizerService.delete(parameters.token)
            call.respond("Organizer is deleted.")
        }
        post("/create"){
            val parameters = call.receive<Organizer>()
            val organizer = organizerService.createOrganizer(parameters)
            if (organizer == null) call.respond(HttpStatusCode.BadRequest,"Organizer isn't created.") else
                call.respond(HttpStatusCode.OK,
                    OrganizerWithoutPswd(
                        id = organizer.id,
                        name = organizer.name,
                        surname = organizer.surname,
                        email = organizer.email,
                        mobile = organizer.mobile,
                        status = organizer.status,
                        token = tokenService.generate(
                        config = tokenConfig,
                TokenClaim(
                name = "userId",
                value = parameters.id.toString()))))
        }
        post("/token"){
            val parameters = call.receive<OrganizerResponse>()
            val organizer = organizerService.selectByToken(parameters.token)
            if (organizer == null) call.respond(HttpStatusCode.BadRequest,"Buyer isn't found.") else
                call.respond(HttpStatusCode.OK,organizer)
        }
        put("/update"){
            val parameters = call.receive<OrganizerWithoutPswd>()
            val organizer = organizerService.updateParamsOrganizer(parameters)
            if (organizer == null) call.respond(HttpStatusCode.BadRequest,"Organizer isn't updated.") else
                call.respond(HttpStatusCode.OK,OrganizerResponse(organizer.token))
        }
        put("/signIn") {
            val parameters = call.receive<OrganizerRequest>()
            val organizer = organizerService.selectByLogin(parameters.login)
            if (organizer?.secret == null) call.respond(HttpStatusCode.Conflict, "Invalid parameters.") else {
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

                call.respond(
                    status = HttpStatusCode.OK,
                    message = OrganizerWithoutPswd(
                        id = organizer.id,
                        name = organizer.name,
                        surname = organizer.surname,
                        email = organizer.email,
                        mobile = organizer.mobile,
                        status = organizer.status,
                        token = organizer.password
                    )
                )
            }
        }
        post("/updateCity"){
            val parameters = call.receive<OrganizerUpdateCity>()
            val result = organizerService.updateCityPerson(parameters.token,parameters.city)
            if(result) call.respond(HttpStatusCode.OK) else call.respond(HttpStatusCode.BadRequest)
        }
        /**
         * Deprecated -> filtration isn't realized
         */
       /* post("/{login}"){
            val loginFromQuery = call.parameters["login"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid organizer id")
            }
            val response = organizerService.selectByLogin(loginFromQuery)
            if (response == null) call.respond(HttpStatusCode.OK, "Login not found") else
                call.respond(HttpStatusCode.Conflict, "Login is created")
        }
        post("/{city}"){
            val cityFromQuery = call.parameters["city"] ?: kotlin.run {
                throw NotFoundException("Please provide a valid city")
            }
            organizerService.selectOrganizerByCity(Cities.valueOf(cityFromQuery))
        }*/
        }
    }
