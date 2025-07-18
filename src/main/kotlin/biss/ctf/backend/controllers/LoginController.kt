package biss.ctf.backend.controllers

import biss.ctf.backend.configuration.LoginConfiguration
import biss.ctf.backend.objects.apiObjects.UserCookieData
import biss.ctf.backend.objects.apiObjects.toUser.LoginResponseToUser
import biss.ctf.backend.services.UserDataService
import biss.ctf.backend.services.login.LoginPasswordServiceFactory
import biss.ctf.backend.utils.PasswordUtils
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.client.HttpServerErrorException
import java.util.*
import kotlin.concurrent.schedule

@RestController
@RequestMapping("/login")
//TODO(98) - not SRP, split logic to service(s)
class LoginController(
    val userDataService: UserDataService,
    val loginConfiguration: LoginConfiguration,
    val loginPasswordServiceFactory: LoginPasswordServiceFactory
) {
    companion object {
        private val logger = KotlinLogging.logger(LoginController::class.java.name)
    }

    @GetMapping
    fun login(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam uuid: String
    ): LoginResponseToUser {
        logger.info { "Got login request from UUID '$uuid' '$username' : '$password'" }

        if (username !in loginConfiguration.allowedUsers.keys) {
            logger.debug { "username '$username' is not one of the allowed usernames" }
            throw HttpServerErrorException(HttpStatus.UNAUTHORIZED, "Username does not exist")
        }

        val userMegama = loginConfiguration.allowedUsers[username]!!
        logger.debug { "User '${uuid}' with username '${username} is attemting to log in profession path '${userMegama}'" }

        val user = userDataService.findOrSaveUserByUuid(uuid)
        logger.debug { "Generated password '${user.password}' for UUID: $uuid" }

        val loginPasswordService = loginPasswordServiceFactory.getLoginPasswordService(userMegama)

        val (passwordResponseData, isPasswordCorrect) = loginPasswordService.handlePasswordAttempt(password, user.password)

        if (isPasswordCorrect) {
            userDataService.setUserLoggedIn(uuid)
            logger.info("Logging in for user \"${user.UUID}\" and password \"${user.password}\"")
        }

        return LoginResponseToUser(
            isPasswordCorrect,
            UserCookieData(uuid, false, username, userMegama).toEncryptedJson(),
            passwordResponseData
        )
    }

    @GetMapping("/doesUserLoggedIn")
    fun doesUserLoggedIn(@RequestParam uuid: String): Boolean {
        return userDataService.isUserLoggedIn(uuid)
    }

    @GetMapping("/is_admin")
    fun isAdminUser(
        @CookieValue("user") userCookie: String,
    ): ResponseEntity<Boolean> {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)
        val isAdmin = cookieData.isAdmin
        return ResponseEntity(isAdmin, if (isAdmin) HttpStatus.OK else HttpStatus.UNAUTHORIZED)
    }
}