package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.toUser.LoginResponseToUser
import biss.ctf.backend.services.LoginService
import biss.ctf.backend.services.UserDataService
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
class LoginController(
    val userDataService: UserDataService,
    val loginService: LoginService
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
        logger.debug { "Got login request from UUID '$uuid' '$username' : '$password'" }

        return this.loginService.handleLoginAttempt(username, password, uuid)
    }

    @GetMapping("/doesUserLoggedIn")
    fun doesUserLoggedIn(@RequestParam uuid: String): Boolean {
        return userDataService.isUserLoggedIn(uuid)
    }

    @GetMapping("/is_admin")
    fun isAdminUser(
        @CookieValue("user") userCookie: String,
    ): ResponseEntity<Boolean> {
        val isAdmin = this.loginService.isUserAdmin(userCookie)

        return ResponseEntity(isAdmin, if (isAdmin) HttpStatus.OK else HttpStatus.UNAUTHORIZED)
    }

    @GetMapping("/please_get_me_admin")
    fun pleaseGetMeAdmin(
        @CookieValue("user") userCookie: String,
    ): String {
        return this.loginService.updateUserAdmin(userCookie)
    }
}