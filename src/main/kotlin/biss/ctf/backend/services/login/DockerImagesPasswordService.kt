package biss.ctf.backend.services.login

import biss.ctf.backend.entities.UserDataEntity
import biss.ctf.backend.objects.routing.CTFStage
import org.springframework.beans.factory.annotation.Value

// TODO - can be generic
class DockerImagesPasswordService() : LoginPasswordService() {
    override val ctfStage: CTFStage = CTFStage.LOGIN_DOCKER_IMAGES

    @Value("docker-images.login-password")
    private lateinit var loginPassword: String

    override fun handlePasswordAttempt(
        passwordAttempt: String,
        user: UserDataEntity
    ): Pair<Any?, Boolean> {
        if (validatePassword(passwordAttempt, user.password)) {
            return null to true
        }

        return null to false
    }

    override fun generatePassword(uuid: String): String {
        return this.loginPassword
    }

    /**
     * First checks whether the password length is the actual password length, and if not - throws exception.
     * This is done as an extra step before reaching the Dudu Faruk Images
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
}