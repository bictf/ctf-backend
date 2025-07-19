package biss.ctf.backend.services.login

import biss.ctf.backend.entities.UserDataEntity
import biss.ctf.backend.objects.routing.CTFStage

/**
 * An abstract class for services that handle the logic of the login page password stages.
 */
abstract class LoginPasswordService {
    abstract val ctfStage: CTFStage

    /**
     * Handles the password attempt from the user with whichever password game/stage is implemented.
     *
     * @return A pair of:
     * - Password return data to be placed in the [biss.ctf.backend.objects.apiObjects.toUser.LoginResponseToUser.passwordData] field.
     * - Whether the password was correct or not.
     */
    abstract fun handlePasswordAttempt(
        passwordAttempt: String,
        user: UserDataEntity
    ): Pair<Any, Boolean>

    abstract fun generatePassword(uuid: String): String

    /**
     * Just checks whether the inputted password is equal to the actual password.
     * INTENDED TO BE OVERRIDDEN with custom logic, see [WordlePasswordService] for an example.
     */
    open fun validatePassword(inputtedPassword: String, actualPassword: String): Boolean =
        inputtedPassword == actualPassword
}