package biss.ctf.backend.controllers

import biss.ctf.backend.exceptions.UnauthorizedException
import biss.ctf.backend.objects.apiObjects.PasswordGameLevelDto
import biss.ctf.backend.objects.apiObjects.SecretUserCookie
import biss.ctf.backend.services.PasswordGameService
import biss.ctf.backend.services.UserDataService
import biss.ctf.backend.services.passwordlevels.PasswordGameLevel
import biss.ctf.backend.services.passwordlevels.ReactivePasswordLevel
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/password_game")
class PasswordGameController(
    private val passwordGameService: PasswordGameService, private val userDataService: UserDataService
) {

    /**
     * This endpoint checks the requested number of levels, to see whether the password is correct for them.
     * If any one level was incorrect, the function returns the list in the specified length.
     * If all levels were solved correctly, fetches all the next levels which the password answers, and add the first one which it is incorrect for.
     */
    @GetMapping("/solve")
    fun solvePasswordLevels(
        @RequestParam password: String, @RequestParam levels: Int, @CookieValue("user") userCookie: String
    ): List<PasswordGameLevelDto> {
        val decryptedCookieData = SecretUserCookie.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(decryptedCookieData.uuid)

        if (!decryptedCookieData.isAdmin) {
            throw UnauthorizedException(decryptedCookieData.uuid, "Admin access required to restore password!")
        }

        if (levels > passwordGameService.getLevelCount()) {
            throw IllegalArgumentException("Requested $levels levels, which exceeded levels available (${passwordGameService.getLevelCount()})")
        }
        val levelsToCheck = passwordGameService.getAllLevels(upTo = levels)
            .map { getDtoForLevel(it, password) }
            .toMutableList()

        val firstIncorrectIndex = passwordGameService.getAllLevels(from = levels)
            .indexOfFirst { !getDtoForLevel(it, password).isCorrect }

        if (firstIncorrectIndex > levels) {
            levelsToCheck.addAll(
                passwordGameService.getAllLevels(from = levels, upTo = firstIncorrectIndex + 1)
                    .map {
                        getDtoForLevel(it, password)
                    })
        }

        return levelsToCheck
    }

    @GetMapping("/does_solve_all")
    fun checkIfSolvedAllLevels(
        @RequestParam password: String,
        @CookieValue("user") userCookie: String
    ): Boolean {
        val decryptedCookieData = SecretUserCookie.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(decryptedCookieData.uuid)
        if (!decryptedCookieData.isAdmin) {
            throw UnauthorizedException(decryptedCookieData.uuid, "Admin access required to play password game!")
        }

        return passwordGameService.getAllLevels().all { it.doesAnswerLevel(password) }
    }

    /**
     * Converts a `PasswordGameLevel` to a `PasswordGameLevelDto`.
     * Handles both standard and reactive password levels.
     *
     * @param it The `PasswordGameLevel` to convert.
     * @param password The password used to check the level.
     * @return A `PasswordGameLevelDto` representing the level's description and whether the password answers it.
     */
    private fun getDtoForLevel(
        it: PasswordGameLevel,
        password: String
    ) = when (it) {
        is ReactivePasswordLevel -> it.invoke(password)
        else -> PasswordGameLevelDto(
            it.getLevelDescription(),
            it.doesAnswerLevel(password)
        )
    }
}