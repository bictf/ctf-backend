package biss.ctf.backend.services.login

import biss.ctf.backend.entities.UserDataEntity
import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.objects.wordle.WordleCharState
import biss.ctf.backend.objects.wordle.WordleResponseData
import biss.ctf.backend.services.UserDataService
import org.springframework.stereotype.Service
import java.util.Collections
import java.util.Timer
import kotlin.concurrent.schedule

typealias WordleDiff = ArrayList<WordleCharState>

private const val MINUTES_TO_RESET_PASSWORD: Long = 1
private const val SECONDS_TO_RESET_PASSWORD: Long = MINUTES_TO_RESET_PASSWORD * 60
private const val MILLISECONDS_TO_RESET_PASSWORD: Long = SECONDS_TO_RESET_PASSWORD * 1000

/**
 * A service responsible for the password validation and wordle game logic.
 */
@Service
class WordlePasswordService(private val userDataService: UserDataService) : LoginPasswordService() {
    override val ctfStage: CTFStage = CTFStage.LOGIN_WORDLE

    override fun handlePasswordAttempt(
        passwordAttempt: String,
        actualPassword: String,
        user: UserDataEntity
    ): Pair<WordleResponseData, Boolean> {
        if (validatePassword(passwordAttempt, actualPassword)) {
            return Pair(
                WordleResponseData(
                    wordleDiff = generateCorrectWordleDiff(passwordAttempt.length),
                    time = MINUTES_TO_RESET_PASSWORD
                ),
                true
            )
        }

        createLogoutTask(user.UUID)

        return Pair(
            WordleResponseData(
                wordleDiff = calculateWordleDiff(passwordAttempt, actualPassword),
                time = MINUTES_TO_RESET_PASSWORD
            ),
            false
        )
    }

    /**
     * First checks whether the password length is the actual password length, and if not - throws exception.
     * This is done so the user will get to the correct length to play the wordle.
     *
     * It then checks whether the password is correct.
     *
     * @param inputtedPassword The attempted password from the user
     * @param actualPassword The correct password
     *
     * @return Boolean value representing whether the password is correct or not
     */
    override fun validatePassword(inputtedPassword: String, actualPassword: String): Boolean {
        if (inputtedPassword.length != actualPassword.length) {
            throw Exception("Password is in the wrong length!")
        }

        return super.validatePassword(inputtedPassword, actualPassword)
    }

    /**
     * Calculates the wordle values for each character in the attempted wordle string.
     * @param attemptedString The attempted wordle string
     * @param actualString The string the user is trying to match
     *
     * @return An arraylist of [WordleCharState] corresponding to each character in the attempted string.
     */
    private fun calculateWordleDiff(attemptedString: String, actualString: String): WordleDiff {
        val wordleDiff: WordleDiff = arrayListOf()

        attemptedString.zip(actualString).forEach { (attemptedChar, actualChar) ->
            when (attemptedChar) {
                actualChar -> wordleDiff.add(WordleCharState.CorrectCharCorrectPlace)
                in actualString -> wordleDiff.add(WordleCharState.CorrectCharWrongPlace)
                else -> wordleDiff.add(WordleCharState.IncorrectChar)
            }
        }

        return wordleDiff
    }

    private fun generateCorrectWordleDiff(characterCount: Int): WordleDiff =
        ArrayList(Collections.nCopies(characterCount, WordleCharState.CorrectCharCorrectPlace))

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
}