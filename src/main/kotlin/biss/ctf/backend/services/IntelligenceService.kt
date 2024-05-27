package biss.ctf.backend.services

import biss.ctf.backend.entities.FileEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class IntelligenceService(
    @Qualifier("intelligenceFiles")
    val intelligenceFiles: List<FileEntity>,
    @Qualifier("binaryFiles")
    val binaryFiles: List<FileEntity>,
) {

    fun findFileWithRegex(search: String): FileEntity {
        val regex = search.toRegex()
        return intelligenceFiles.find { regex.matches(it.title) } ?: findBinaryFileByName(search)
    }

    fun countFilesWithRegex(search: String): Int {
        val regex = search.toRegex()
        return intelligenceFiles.count { regex.matches(it.title) } + binaryFiles.count { regex.matches(it.title) }
    }

    fun findBinaryFileByName(fileName: String): FileEntity {
        val nonRegexSearch = binaryFiles.find { it.isBinaryFile && it.title == fileName }
        val regexSearch = binaryFiles.find { fileName.toRegex().matches(it.title) }
        return nonRegexSearch ?: regexSearch ?: FileEntity("", "")
    }
}