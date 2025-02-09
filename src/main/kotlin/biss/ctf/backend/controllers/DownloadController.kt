package biss.ctf.backend.controllers

import biss.ctf.backend.exceptions.UnauthorizedException
import biss.ctf.backend.objects.apiObjects.SecretUserCookie
import biss.ctf.backend.services.IntelligenceService
import biss.ctf.backend.services.PasswordGameService
import biss.ctf.backend.services.UserDataService
import jakarta.servlet.http.HttpServletResponse
import kotlinx.serialization.SerializationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.FileNotFoundException


@RestController
@RequestMapping("/download")
class DownloadController(
    val intelligenceService: IntelligenceService,
    val userDataService: UserDataService,
    val passwordGameService: PasswordGameService,
    @Value("\${secret_cookie_value:}") val secretCookieFieldValue: String
) {
    @GetMapping
    fun downloadBinaryFile(
        @CookieValue("user") userCookie: String,
        @RequestParam fileName: String,
        @RequestParam password: String,
        response: HttpServletResponse
    ): ResponseEntity<ByteArray> {
        val userDecryptedCookie: SecretUserCookie = try {
            SecretUserCookie.fromEncryptedJson(userCookie)
        } catch (serializationException: SerializationException) {
            if ("secret" in serializationException.message!!) {
                throw Exception("Missing secret Field In Cookie. Good Thing The Zionists Will Never Find It, I Keep It On My Personal Computer!")
            } else {
                throw serializationException
            }
        }

        userDataService.assertIsLoggedIn(userDecryptedCookie.uuid)

        if (!userDecryptedCookie.isAdmin) {
            throw UnauthorizedException(userDecryptedCookie.uuid, "Admin access required to download file!")
        }

        if (!passwordGameService.getAllLevels().all { it.doesAnswerLevel(password) }) {
            throw UnauthorizedException(userDecryptedCookie.uuid, "File vault password was incorrect!")
        }

        if (secretCookieFieldValue != userDecryptedCookie.secret) {
            throw Exception("Secret cookie field is incorrect!")
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