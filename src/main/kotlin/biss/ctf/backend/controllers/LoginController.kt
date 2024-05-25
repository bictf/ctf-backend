package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.UserCookieData
import biss.ctf.backend.objects.apiObjects.toUser.LoginResponseToUser
import biss.ctf.backend.services.UserDataService
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
class LoginController(
    val userDataService: UserDataService,
) {
    companion object {
        private val logger = KotlinLogging.logger(LoginController::class.java.name)
        private const val MINUTES_TO_RESET_PASSWORD: Long = 1
        private const val SECONDS_TO_RESET_PASSWORD: Long = MINUTES_TO_RESET_PASSWORD * 60
        private const val MILLISECONDS_TO_RESET_PASSWORD: Long = SECONDS_TO_RESET_PASSWORD * 1000
    }

    /**
     * Creates a task to log a user out in [SECONDS_TO_RESET_PASSWORD]
     */
    private fun createLogoutTask(uuid: String) {
        Timer().schedule(MILLISECONDS_TO_RESET_PASSWORD) {
            if (!userDataService.isUserLoggedIn(uuid)) {
                userDataService.expireUserPassword(uuid)
            }
        }
    }

    @GetMapping
    fun login(
        @RequestParam username: String,
        @RequestParam password: String,
        @RequestParam uuid: String
    ): LoginResponseToUser {
        logger.info { "Got login request from UUID '$uuid' '$username' : '$password'" }

        if (username != "muhammad") {
            throw HttpServerErrorException(HttpStatus.UNAUTHORIZED, "Username does not exist")
        }

        val user = userDataService.findOrSaveUserByUuid(uuid)
        logger.info { "Retrieved password '${user.password}' for UUID: $uuid" }
        val passwordDiff = PasswordUtils.checkPasswordsDiff(user.password, password)
        val isPasswordTrue = PasswordUtils.isPasswordTrue(passwordDiff)

        if (isPasswordTrue) {
            userDataService.setUserLoggedIn(uuid)
            logger.info("Logging in for user \"${user.UUID}\" and password \"${user.password}\"")
        } else {
            createLogoutTask(uuid)
        }

        return LoginResponseToUser(
            isPasswordTrue,
            passwordDiff,
            UserCookieData(uuid, false, "muhammad").toEncryptedJson(),
            MINUTES_TO_RESET_PASSWORD
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

    @ExceptionHandler(Exception::class)
    fun customerNotFound(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.message)
    }
}