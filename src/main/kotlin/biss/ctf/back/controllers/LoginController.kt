package biss.ctf.back.controllers

import biss.ctf.back.objects.apiObjects.toUser.LoginResponseToUser
import biss.ctf.back.services.EncryptService
import biss.ctf.back.services.PasswordService
import biss.ctf.back.services.UserDataService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import kotlin.concurrent.schedule

@RestController
@RequestMapping("/login")
class LoginController(
    @Autowired val userDataService: UserDataService,
    @Autowired val passwordService: PasswordService,
    @Autowired val encryptService: EncryptService
) {
    private val logger = KotlinLogging.logger {}
    private val TIME_TO_RESET_PASSWORD_IN_MIN: Long = 1

    @GetMapping
    fun login(@RequestParam username: String, @RequestParam password: String, @RequestParam uuid: String): LoginResponseToUser {
        if (username != "muhammad"){
            throw Exception("User name is invalid")
        }

        val user = userDataService.getUserByUUID(uuid)
        val passwordDiff = passwordService.checkPasswordsDiff(user.password, password)
        val isPasswordTrue = passwordService.isPasswordTrue(passwordDiff)

        var cookie = "{}"
        if (isPasswordTrue){
            cookie = encryptService.encrypt("""{"username":"muhammad", "isAdmin":false}""")
            userDataService.userLoggedIn(uuid)

            logger.info("Logging in for user \"${user.UUID}\" and password \"${user.password}\"")
        }
        else{
            Timer().schedule( (TIME_TO_RESET_PASSWORD_IN_MIN * 60 * 1000)) {
                if(!userDataService.doesUserLoggedIn(uuid)){
                    userDataService.userLoggedOut(uuid)
                }
            }
        }
        
        return LoginResponseToUser(isPasswordTrue, passwordDiff, cookie, TIME_TO_RESET_PASSWORD_IN_MIN)
    }

    @GetMapping("/doesUserLoggedIn")
    fun doesUserLoggedIn(@RequestParam uuid: String): Boolean {
        return userDataService.doesUserLoggedIn(uuid)
    }

    @ExceptionHandler(Exception::class)
    fun customerNotFound(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.status(401).body(exception.message)
    }
}