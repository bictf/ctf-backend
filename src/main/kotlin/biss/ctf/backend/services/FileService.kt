package biss.ctf.back.services

import biss.ctf.back.repositories.FileRepository
import org.springframework.stereotype.Service

@Service
class FileService(
    val fileRepository: FileRepository
) {
    fun findFileWithRegex(search: String) = fileRepository.findFileWithRegex(search)

    fun countFilesWithRegex(search: String) = fileRepository.countFilesWithRegex(search)

    fun findBinaryFileByName(fileName: String) = fileRepository.findBinaryFileByName(fileName)
}