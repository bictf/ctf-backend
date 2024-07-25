package biss.ctf.backend.controllers

import biss.ctf.backend.exceptions.UnauthorizedException
import biss.ctf.backend.objects.apiObjects.UserCookieData
import biss.ctf.backend.services.IntelligenceService
import biss.ctf.backend.services.PasswordGameService
import biss.ctf.backend.services.UserDataService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.FileNotFoundException


@RestController
@RequestMapping("/download")
class DownloadController(
    val intelligenceService: IntelligenceService,
    val userDataService: UserDataService,
    val passwordGameService: PasswordGameService
) {
    @GetMapping
    fun downloadBinaryFile(
        @CookieValue("user") userCookie: String,
        @RequestParam fileName: String,
        @RequestParam password: String,
        response: HttpServletResponse
    ): ResponseEntity<ByteArray> {
        val userDecryptedCookie = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(userDecryptedCookie.uuid)

        if (!userDecryptedCookie.isAdmin) {
            throw UnauthorizedException(userDecryptedCookie.uuid, "Admin access required to download file!")
        }

        if (!passwordGameService.getAllLevels().all { it.doesAnswerLevel(password) }) {
            throw UnauthorizedException(userDecryptedCookie.uuid, "File vault password was incorrect!")
        }

        val file = intelligenceService.findBinaryFileByName(fileName)

        response.status = HttpServletResponse.SC_OK
        response.addHeader("Content-Disposition", "attachment; filename=\"TobSecretFile.zib\"")

        if (!file.isBinaryFile) {
            throw FileNotFoundException()
        }

        return ResponseEntity<ByteArray>(
            intelligenceService.findBinaryFileByName(fileName).file.readBytes(),
            HttpStatus.OK
        )
    }
}