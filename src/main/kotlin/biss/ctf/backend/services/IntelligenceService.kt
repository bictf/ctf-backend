package biss.ctf.backend.services

import biss.ctf.backend.entities.FileEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import java.io.FileNotFoundException

@Service
class IntelligenceService(
    @Qualifier("intelligenceFiles")
    val intelligenceFiles: List<FileEntity>,
    @Qualifier("binaryFiles")
    val binaryFiles: List<FileEntity>,
) {

    fun findFileWithRegex(search: String): FileEntity {
        val regex = search.toRegex()
        return intelligenceFiles.find { regex.matches(it.title) } ?: binaryFiles.find { regex.matches(it.title) }
        ?: throw FileNotFoundException("Nothing matched!")
    }

    fun countFilesWithRegex(search: String): Int {
        val regex = search.toRegex()
        return intelligenceFiles.count { regex.matches(it.title) } + binaryFiles.count { regex.matches(it.title) }
    }

    fun findBinaryFileByName(fileName: String): FileEntity {
        return binaryFiles.find { it.isBinaryFile && it.title == fileName } ?: FileEntity("", "")
    }

    @ExceptionHandler(FileNotFoundException::class)
    fun fileNotFound(exception: Exception): ResponseEntity<String> {
        return ResponseEntity(exception.message, HttpStatus.NOT_FOUND)
    }
}