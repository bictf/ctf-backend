package biss.ctf.back.services

import biss.ctf.back.entities.MazeLevelEntity
import biss.ctf.back.repositories.MazeRepository
import net.lingala.zip4j.ZipFile
import net.lingala.zip4j.model.ZipParameters
import net.lingala.zip4j.model.enums.CompressionLevel
import net.lingala.zip4j.model.enums.EncryptionMethod
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileWriter

const val PRIZE_FOLDER = "src/main/resources/loot"
const val SEND_BOX_FOLDER = "src/main/resources/zipperSendBox"
const val NEXT_LOOT_FOLDER = "src/main/resources/zipperSendBox/nextLoot"
const val NEXT_TRASH_FOLDER = "src/main/resources/zipperSendBox/nextTrash"
const val RESULT_FOLDER = "src/main/resources/zipperResult"


const val LOOT = "$SEND_BOX_FOLDER/loot.zip"
const val TRASH = "$SEND_BOX_FOLDER/trash.zip"
const val RESULT = "$RESULT_FOLDER/TopSecretFile.zip"


@Service
class ZipMazeService(
    val mazeRepository: MazeRepository
) {
    fun createZipMaze(){
        File(RESULT).delete()

        cleanNextLootAndNextTrash()
        cleanLootAndTrash()

        setUpSendBoxWithLootAndTrash()

        for(level in mazeRepository.getLevels()){
            buildMazeLevel(level)
            cleanNextLootAndNextTrash()
        }

        File(LOOT).copyTo(File(RESULT))

        cleanNextLootAndNextTrash()
        cleanLootAndTrash()
    }

    fun setUpSendBoxWithLootAndTrash(){
        zipFolderContent(File(PRIZE_FOLDER), File(LOOT))
        zipFolderContent(File(PRIZE_FOLDER), File(TRASH), "not_today_this_is_dead_end_password!!!")
    }

    fun cleanLootAndTrash(){
        File(LOOT).delete()
        File(TRASH).delete()
    }

    fun cleanNextLootAndNextTrash(){
        deleteFolderContent(File(NEXT_LOOT_FOLDER))
        deleteFolderContent(File(NEXT_TRASH_FOLDER))
    }

    fun buildMazeLevel(level: MazeLevelEntity){
        val lootWriter = FileWriter("$NEXT_LOOT_FOLDER/README.txt")
        lootWriter.write(level.readmeText)
        lootWriter.close()

        val trashWriter = FileWriter("$NEXT_TRASH_FOLDER/README.txt")
        trashWriter.write(level.readmeText)
        trashWriter.close()

        File(LOOT).copyTo(File("$NEXT_LOOT_FOLDER/${level.rightAnswer}.zip"))

        for (answer in level.wrongAnswers){
            File(TRASH).copyTo(File("$NEXT_LOOT_FOLDER/$answer.zip"))
        }

        File(TRASH).copyTo(File("$NEXT_TRASH_FOLDER/${level.rightAnswer}.zip"))

        for (answer in level.wrongAnswers){
            File(TRASH).copyTo(File("$NEXT_TRASH_FOLDER/$answer.zip"))
        }

        cleanLootAndTrash()

        zipFolderContent(folder=File(NEXT_LOOT_FOLDER), dst=File(LOOT), level.password)
        zipFolderContent(folder=File(NEXT_TRASH_FOLDER), dst=File(TRASH), level.password)
    }

    fun zipFolderContent(folder: File, dst: File){
        val zipParameters = ZipParameters()

        val zipFile = ZipFile(dst)
        for (file in folder.listFiles() ?: emptyArray()){
            zipFile.addFile(file, zipParameters)
        }
    }

    fun zipFolderContent(folder: File, dst: File, password:String){


        val zipFile = ZipFile(dst, password.toCharArray())
        for (file in folder.listFiles() ?: emptyArray()){
            zipFile.addFile(file, getEncryptParameters(file))
        }
    }

    fun deleteFolderContent(folder: File) {
        for (file in folder.listFiles() ?: emptyArray()) {
            file.delete()
        }
    }

    fun getEncryptParameters(file: File): ZipParameters {
        if (file.isFile && file.name == "README.txt"){
            return ZipParameters()
        }

        val zipParameters = ZipParameters()
        zipParameters.isEncryptFiles = true
        zipParameters.compressionLevel = CompressionLevel.NORMAL
        zipParameters.encryptionMethod = EncryptionMethod.AES

        return zipParameters
    }
}