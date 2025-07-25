package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.SecretUserCookie
import biss.ctf.backend.objects.apiObjects.toUser.SearchResponseToUser
import biss.ctf.backend.services.IntelligenceService
import biss.ctf.backend.services.UserDataService
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
@Profile("!password-test")
class SearchController(
    val intelligenceService: IntelligenceService,
    val userDataService: UserDataService
) {

    @GetMapping
    fun search(
        @RequestParam text: String,
        @CookieValue("user") userCookie: String
    ): SearchResponseToUser {
        userDataService.assertIsLoggedIn(SecretUserCookie.fromEncryptedJson(userCookie).uuid)
        val numberOfMatchFiles = intelligenceService.countFilesWithRegex(text)
        val firstMatchFile = intelligenceService.findFileWithRegex(text)

        return SearchResponseToUser(numberOfMatchFiles.toLong(), firstMatchFile)
    }
}