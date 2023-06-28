package biss.ctf.back.controllers

import biss.ctf.back.objects.apiObjects.toUser.LoginResponseToUser
import biss.ctf.back.services.EncryptService
import biss.ctf.back.services.PasswordService
import biss.ctf.back.services.UserDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
class LoginController(
    @Autowired val userDataService: UserDataService,
    @Autowired val passwordService: PasswordService,
    @Autowired val encryptService: EncryptService
) {

    @GetMapping
    fun login(@RequestParam username: String, @RequestParam password: String, @RequestParam uuid: String): LoginResponseToUser {

        val user = userDataService.getUserByUUID(uuid)
        val passwordDiff = passwordService.checkPasswordsDiff(user.password, password)
        val isPasswordTrue = passwordService.isPasswordTrue(passwordDiff)

        var cookie = "{}"
        if (isPasswordTrue){
            cookie = """{"username":"admin", "isAdmin":true}"""
            userDataService.userLoggedIn(uuid)
        }

        return LoginResponseToUser(isPasswordTrue, passwordDiff, encryptService.encrypt(cookie))
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