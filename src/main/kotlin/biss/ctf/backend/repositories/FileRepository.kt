package biss.ctf.back.repositories

import biss.ctf.back.DB.FILES_DB
import biss.ctf.back.entities.FileEntity
import biss.ctf.back.entities.UserDataEntity
import org.springframework.stereotype.Repository
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*
import java.util.Collections.emptyMap

@Repository
class FileRepository {
    fun findFileWithRegex(search: String): FileEntity {
        val regex = search.toRegex()

        for (file in FILES_DB){
            if (regex.matches(file.title)){
                return file
            }
        }

        return FileEntity("", "")
    }

    fun countFilesWithRegex(search: String): Long {
        val regex = search.toRegex()
        var fileCounter: Long = 0

        for (file in FILES_DB){
            if (regex.matches(file.title)){
                fileCounter++
            }
        }

        return fileCounter
    }

    fun findBinaryFileByName(fileName: String): FileEntity {
        for (file in FILES_DB){
            if (file.isBinaryFile && file.title == fileName){
                return file
            }
        }

        return FileEntity("", "")
    }
}