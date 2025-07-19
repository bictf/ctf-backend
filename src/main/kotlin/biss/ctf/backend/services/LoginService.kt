package biss.ctf.backend.services

import biss.ctf.backend.configuration.LoginConfiguration
import biss.ctf.backend.objects.apiObjects.SecretUserCookie
import biss.ctf.backend.objects.apiObjects.toUser.LoginResponseToUser
import biss.ctf.backend.services.login.LoginPasswordServiceFactory
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpServerErrorException

/**
 * Handles the logic for requests for [biss.ctf.backend.controllers.LoginController]
 */
@Service
class LoginService(
    private val loginConfiguration: LoginConfiguration,
    private val loginPasswordServiceFactory: LoginPasswordServiceFactory,
    private val userDataService: UserDataService,
    private val userStageService: UserStageService
) {
    companion object {
        private val logger = KotlinLogging.logger(LoginService::class.java.name)
    }

    /**
     * Gets login data from user, and does the following:
     * - Checks username is valid
     * - Gets user's megama
     * - Gets the corresponding [biss.ctf.backend.services.login.LoginPasswordService] and handles the password attempt accordingly
     * - If password correct, logs the user in to the website
     */
    fun handleLoginAttempt(username: String, password: String, uuid: String): LoginResponseToUser {
        if (username !in loginConfiguration.allowedUsers.keys) {
            logger.debug { "username '$username' is not one of the allowed usernames" }
            throw HttpServerErrorException(HttpStatus.UNAUTHORIZED, "Username does not exist")
        }

        val userMegama = loginConfiguration.allowedUsers[username]!!
        logger.debug { "User '${uuid}' with username '${username} is attemting to log in profession path '${userMegama}'" }

        val loginPasswordService = loginPasswordServiceFactory.getLoginPasswordService(userMegama)

        val user = userDataService.findOrSaveUser(uuid, userMegama)
        logger.info { "Retrieved password '${user.password}' for UUID: $uuid" }

        userStageService.saveUserStage(uuid, loginPasswordService.ctfStage)

        val (passwordResponseData, isPasswordCorrect) = loginPasswordService.handlePasswordAttempt(
            password,
            user
        )

        if (isPasswordCorrect) {
            userDataService.setUserLoggedIn(uuid)
            logger.info("Logging in for user \"${user.UUID}\" and password \"${user.password}\"")
        }

        return LoginResponseToUser(
            success = isPasswordCorrect,
            cookie = SecretUserCookie(uuid, false, username).toEncryptedJson(),
            loginType = loginPasswordService.ctfStage,
            passwordData = passwordResponseData
        )
    }

    /**
     * checks whether a user is an admin from its cookie
     */
    fun isUserAdmin(userCookie: String): Boolean {
        val cookieData = SecretUserCookie.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(cookieData.uuid)
        return cookieData.isAdmin
    }
}