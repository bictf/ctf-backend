package biss.ctf.back.controllers

import biss.ctf.back.objects.apiObjects.toUser.LoginResponseToUser
import biss.ctf.back.services.PasswordService
import biss.ctf.back.services.UserDataService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/login")
class LoginController(
        val userDataService: UserDataService,
        val passwordService: PasswordService
) {

    @GetMapping
    fun login(@RequestParam username: String, @RequestParam password: String): LoginResponseToUser {
        val user = userDataService.getUserByIp("11")

        val passwordDiff = passwordService.checkPasswordsDiff(user.password, password)
        val isPasswordTrue = passwordService.isPasswordTrue(passwordDiff)

        return LoginResponseToUser(isPasswordTrue, passwordDiff)
    }

    @ExceptionHandler(Exception::class)
    fun customerNotFound(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(exception.message)
    }
}