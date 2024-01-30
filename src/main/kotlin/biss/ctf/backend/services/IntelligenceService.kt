package biss.ctf.backend.services

import biss.ctf.backend.entities.FileEntity
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class IntelligenceService(
    @Qualifier("intelligenceFiles")
    val intelligenceFiles: Sequence<FileEntity>,
    @Qualifier("binaryFiles")
    val binaryFiles: Sequence<FileEntity>,
) {

    fun findFileWithRegex(search: String): FileEntity {
        val regex = search.toRegex()
        return intelligenceFiles.find { regex.matches(it.title) } ?: FileEntity("", "")
    }

    fun countFilesWithRegex(search: String): Int {
        val regex = search.toRegex()
        return intelligenceFiles.count { regex.matches(it.title) }
    }

    fun findBinaryFileByName(fileName: String): FileEntity {
        return binaryFiles.find { it.isBinaryFile && it.title == fileName } ?: FileEntity("", "")
    }
}