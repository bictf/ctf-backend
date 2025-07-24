package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.SecretUserCookie
import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.services.UserDataService
import biss.ctf.backend.services.routing.StageRouter
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * A controller responsible for the CTF stage routing.
 */
@RestController
@RequestMapping("/stage")
class StageController(private val stageRouter: StageRouter, private val userDataService: UserDataService) {
    /**
     * Gets the next stage for the requesting user.
     * @param userCookie The cookie value of the user, from which we extract the UUID and use it to get the next stage.
     */
    @GetMapping("/next")
    fun getNextStage(@CookieValue("user") userCookie: String): CTFStage {
        val decryptedUserCookie = SecretUserCookie.fromEncryptedJson(userCookie)

        userDataService.assertIsLoggedIn(decryptedUserCookie.uuid)

        return stageRouter.getUserNextStage(decryptedUserCookie.uuid)
    }
}