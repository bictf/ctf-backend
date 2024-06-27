package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.PasswordGameLevelDto
import biss.ctf.backend.objects.apiObjects.UserCookieData
import biss.ctf.backend.services.PasswordGameService
import biss.ctf.backend.services.UserDataService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/password-game")
class PasswordGameController(
    private val passwordGameService: PasswordGameService,
    private val userDataService: UserDataService
) {
    @GetMapping("/next_level")
    fun getNextPasswordLevel(
        @RequestParam password: String,
        @CookieValue("user") userCookie: String
    ): PasswordGameLevelDto {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)
        return PasswordGameLevelDto(passwordGameService.getNextLevel(password).getLevelDescription(), false)
    }

    @GetMapping("/solve")
    fun getSolvePasswordLevel(
        @RequestParam password: String,
        @RequestParam levels: Int,
        @CookieValue("user") userCookie: String
    ): List<PasswordGameLevelDto> {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)

        if (levels > passwordGameService.getLevelCount()) {
            throw IllegalArgumentException("Requested $levels levels, which exceeded levels available (${passwordGameService.getLevelCount()})")
        }

        val levelsToCheck = passwordGameService.getAllLevels().subList(0, levels).toMutableList()
        if (levelsToCheck.all { it.doesAnswerLevel(password) }) {
            levelsToCheck += passwordGameService.getNextLevel(password)
        }

        return levelsToCheck.map { PasswordGameLevelDto(it.getLevelDescription(), it.doesAnswerLevel(password)) }
    }

    @GetMapping("/does_solve_all")
    fun checkIfSolvedAllLevels(@RequestParam password: String, @CookieValue("user") userCookie: String): Boolean {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)

        return passwordGameService.getAllLevels().all { it.doesAnswerLevel(password) }
    }

    @GetMapping("/remaining_levels")
    fun getRemainingLevels(@RequestParam password: String, @CookieValue("user") userCookie: String): Int {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)

        return passwordGameService.getAllLevels().size - passwordGameService.getAllLevels()
            .filter { !it.doesAnswerLevel(password) }.size
    }
}