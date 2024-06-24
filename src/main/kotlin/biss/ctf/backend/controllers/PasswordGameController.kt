package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.PasswordGameLevelDto
import biss.ctf.backend.objects.apiObjects.UserCookieData
import biss.ctf.backend.services.PasswordGameService
import biss.ctf.backend.services.UserDataService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PasswordGameController(
    private val passwordGameService: PasswordGameService,
    private val userDataService: UserDataService
) {
    @GetMapping("/next_password_level")
    fun getNextPasswordLevel(
        @RequestParam password: String,
        @CookieValue("user") userCookie: String
    ): PasswordGameLevelDto {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)
        return PasswordGameLevelDto(passwordGameService.getNextLevel(password).getLevelDescription(), false)
    }

    @GetMapping("/password_levels/solve")
    fun getSolvePasswordLevel(
        @RequestParam password: String,
        @RequestParam levels: Int,
        @CookieValue("user") userCookie: String
    ): List<PasswordGameLevelDto> {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)
        val levelsToCheck = passwordGameService.getAllLevels().subList(0, levels).toMutableList()
        if(levelsToCheck.all{it.doesAnswerLevel(password)}) {
            levelsToCheck +=  passwordGameService.getNextLevel(password)
        }
        return levelsToCheck.map { PasswordGameLevelDto(it.getLevelDescription(), it.doesAnswerLevel(password)) }
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNoSuchElementException(e: NoSuchElementException) = ResponseEntity<String>(HttpStatus.NOT_FOUND)
}