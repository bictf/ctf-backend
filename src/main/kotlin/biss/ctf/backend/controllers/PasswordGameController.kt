package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.PasswordGameLevelDto
import biss.ctf.backend.objects.apiObjects.UserCookieData
import biss.ctf.backend.services.PasswordGameService
import biss.ctf.backend.services.UserDataService
import biss.ctf.backend.services.passwordlevels.PasswordGameLevel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/password_game")
class PasswordGameController(
    private val passwordGameService: PasswordGameService, private val userDataService: UserDataService
) {
    private val allLevels: List<PasswordGameLevel>
        get() = passwordGameService.getAllLevels()

    /**
     * This endpoint checks the requested number of levels, to see whether the password is correct for them.
     * If any one level was incorrect, the function returns the list in the specified length.
     * If all levels were solved correctly, fetches all the next levels which the password answers, and add the first one which it is incorrect for.
     */
    @GetMapping("/solve")
    fun solvePasswordLevels(
        @RequestParam password: String, @RequestParam levels: Int, @CookieValue("user") userCookie: String
    ): List<PasswordGameLevelDto> {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)

        if (levels > passwordGameService.getLevelCount()) {
            throw IllegalArgumentException("Requested $levels levels, which exceeded levels available (${passwordGameService.getLevelCount()})")
        }
        val levelsToCheck = allLevels
            .subList(0, levels)
            .map { PasswordGameLevelDto(it.getLevelDescription(), it.doesAnswerLevel(password)) }
            .toMutableList()

        val firstIncorrectIndex = allLevels
            .subList(levels, allLevels.size)
            .indexOfFirst { !it.doesAnswerLevel(password) }

        if (firstIncorrectIndex > levels) {
            levelsToCheck.addAll(allLevels.subList(levels, firstIncorrectIndex + 1)
                .map { PasswordGameLevelDto(it.getLevelDescription(), it.doesAnswerLevel(password)) })
        }

        return levelsToCheck
    }

    @GetMapping("/does_solve_all")
    fun checkIfSolvedAllLevels(
        @RequestParam password: String,
        @CookieValue("user") userCookie: String
    ): Boolean {
        val cookieData = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)

        return allLevels.all { it.doesAnswerLevel(password) }
    }
}