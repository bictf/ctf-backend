package biss.ctf.back.controllers

import biss.ctf.back.objects.apiObjects.toUser.LoginResponseToUser
import biss.ctf.back.services.PasswordService
import biss.ctf.back.services.UserDataService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
class LoginController(
    @Autowired val userDataService: UserDataService,
    @Autowired val passwordService: PasswordService
) {

    @GetMapping
    fun login(@RequestParam username: String, @RequestParam password: String, @RequestParam uuid: String): LoginResponseToUser {

        val user = userDataService.getUserByUUID(uuid)
        val passwordDiff = passwordService.checkPasswordsDiff(user.password, password)
        val isPasswordTrue = passwordService.isPasswordTrue(passwordDiff)

        var cookie = "{}"
        if (isPasswordTrue){
            cookie = """{"username":"admin", "isAdmin":false}"""
        }

        return LoginResponseToUser(isPasswordTrue, passwordDiff, cookie)
    }

    @ExceptionHandler(Exception::class)
    fun customerNotFound(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(exception.message)
    }
}