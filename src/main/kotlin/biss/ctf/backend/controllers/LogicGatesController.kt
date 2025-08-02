package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.SecretUserCookie
import biss.ctf.backend.services.UserDataService
import biss.ctf.backend.services.logicgates.LogicGatesService
import biss.ctf.backend.services.logicgates.LookUpTable
import org.springframework.context.annotation.Profile
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/logic")
@Profile("!password-test")
class LogicGatesController(
    private val userDataService: UserDataService,
    private val logicGatesService: LogicGatesService
) {
    // TODO(115) - add LUT obfuscation
    @GetMapping("/luts")
    fun getLookUpTables(
        @RequestParam lutSeeds: List<String>,
        @CookieValue("user") userCookie: String
    ): List<LookUpTable> {
        userDataService.assertIsLoggedIn(SecretUserCookie.fromEncryptedJson(userCookie).uuid)

        val lookUpTables = mutableListOf<LookUpTable>()
        for (lutSeed in lutSeeds) {
            lookUpTables.add(this.logicGatesService.generateLookupTable(lutSeed.hashCode().toLong()))
        }

        return lookUpTables
    }
}