package biss.ctf.backend.controllers

import biss.ctf.backend.objects.apiObjects.UserCookieData
import biss.ctf.backend.services.IntelligenceService
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
    val userDataService: UserDataService
) {
    @GetMapping
    fun downloadBinaryFile(
        @CookieValue("user") userCookie: String,
        @RequestParam fileName: String,
        response: HttpServletResponse
    ): ResponseEntity<ByteArray> {
        val userDecryptedCookie = UserCookieData.fromEncryptedJson(userCookie)
        userDataService.assertIsLoggedIn(userDecryptedCookie.uuid)
        if (userDecryptedCookie.isAdmin) {
            return ResponseEntity<ByteArray>("User is unauthorized!".toByteArray(), HttpStatus.UNAUTHORIZED)
        }
        val file = intelligenceService.findBinaryFileByName(fileName)

        response.status = HttpServletResponse.SC_OK
        response.addHeader("Content-Disposition", "attachment; filename=\"TobSecretFile.zib\"")

        if (!file.isBinaryFile) {
            throw FileNotFoundException()
        }

        return ResponseEntity<ByteArray>(intelligenceService.findBinaryFileByName(fileName).file.readBytes(), HttpStatus.OK)
    }
}