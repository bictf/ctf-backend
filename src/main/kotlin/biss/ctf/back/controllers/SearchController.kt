package biss.ctf.back.controllers

import biss.ctf.back.objects.apiObjects.toUser.SearchResponseToUser
import biss.ctf.back.services.FileService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
class SearchController(
        val fileService: FileService
) {

    @GetMapping
    fun login(@RequestParam text: String): SearchResponseToUser {
        val numberOfMatchFiles = fileService.countFilesWithRegex(text)
        val firstMatchFile = fileService.findFileWithRegex(text)

        return SearchResponseToUser(numberOfMatchFiles, firstMatchFile)
    }

    @ExceptionHandler(Exception::class)
    fun customerNotFound(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(exception.message)
    }
}