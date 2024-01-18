package biss.ctf.back.controllers

import biss.ctf.back.services.FileService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.*


@RestController
@RequestMapping("/download")
class DownloadController(
    val fileService: FileService,
) {
    @GetMapping
    fun downloadBinaryFile(@RequestParam fileName: String, response: HttpServletResponse): ByteArray {
        val file = fileService.findBinaryFileByName(fileName)

        response.status = HttpServletResponse.SC_OK
        response.addHeader("Content-Disposition", "attachment; filename=\"TobSecretFile.zib\"")

        if (!file.isBinaryFile) {
            throw FileNotFoundException()
        }

        return fileService.findBinaryFileByName(fileName).file.readBytes()
    }

    @ExceptionHandler(Exception::class)
    fun customerNotFound(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(exception.message)
    }
}