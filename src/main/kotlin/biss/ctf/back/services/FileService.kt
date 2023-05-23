package biss.ctf.back.services

import biss.ctf.back.repositories.FileRepository
import biss.ctf.back.repositories.UserDataRepository
import org.springframework.stereotype.Service
import java.io.FileNotFoundException

@Service
class FileService(
        val fileRepository: FileRepository
) {
    fun findFileWithRegex(search: String) = fileRepository.findFileWithRegex(search)

    fun countFilesWithRegex(search: String) = fileRepository.countFilesWithRegex(search)
}