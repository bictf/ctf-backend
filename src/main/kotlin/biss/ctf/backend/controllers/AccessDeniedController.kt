package biss.ctf.backend.controllers

import biss.ctf.backend.configuration.AccessDeniedConfig
import biss.ctf.backend.objects.apiObjects.SecretUserCookie
import biss.ctf.backend.services.UserDataService
import mu.KotlinLogging
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/access-denied")
class AccessDeniedController(
    private val userDataService: UserDataService,
    private val accessDeniedConfig: AccessDeniedConfig
) {

    private val logger = KotlinLogging.logger { this.javaClass.name }

    @GetMapping("/message")
    fun getMessage(
        @CookieValue("user") userCookie: String,
    ): String {
        val cookie = SecretUserCookie.fromEncryptedJson(userCookie)
        logger.info { "Getting access denied cookie message for user: ${cookie.uuid}" }

        val user = userDataService.findUserByUuid(cookie.uuid)
        if (user == null) {
            return accessDeniedConfig.noSuchUserMessage
        }
        if (!user.hasLoggedIn) {
            return accessDeniedConfig.notLoggedInMessage
        }

        if (!cookie.isAdmin) {
            return accessDeniedConfig.megamaCookieMessages.getOrElse(user.megama) { accessDeniedConfig.defaultCookieMessage }
        }

        return accessDeniedConfig.defaultMessage
    }
}