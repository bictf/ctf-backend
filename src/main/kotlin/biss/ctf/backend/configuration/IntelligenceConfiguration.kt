package biss.ctf.backend.configuration

import biss.ctf.backend.entities.FileEntity
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.io.File

@Configuration
@Profile("!password-test")
class IntelligenceConfiguration {
    companion object {
        val logger = KotlinLogging.logger(IntelligenceConfiguration::class.java.name)
    }

    @Bean
    @Qualifier("intelligenceFiles")
    fun getIntelligenceFiles(
        @Value("\${intelligence.root_folder}") intelligenceFilesRoot: String
    ): List<FileEntity> {
        val intelligenceFolder = File(intelligenceFilesRoot)
        if (!intelligenceFolder.isDirectory) {
            throw IllegalArgumentException("$intelligenceFilesRoot is not  a directory!")
        }
        logger.info("Loading intelligence files into memory!")
        return intelligenceFolder.listFiles()?.map {
            FileEntity(it.nameWithoutExtension, it.readText(), false, it)
        } ?: emptyList()
    }

    @Bean
    @Qualifier("binaryFiles")
    fun getBinaryFiles(
        @Value("\${binaries.root_folder}") intelligenceFilesRoot: String
    ): List<FileEntity> {
        val binariesFolder = File(intelligenceFilesRoot)
        if (!binariesFolder.isDirectory) {
            throw IllegalArgumentException("$intelligenceFilesRoot is not  a directory!")
        }
        logger.info("Loading binary file references into memory!")
        return binariesFolder.listFiles()?.map {
            FileEntity(it.nameWithoutExtension, "?????????", true, it)
        } ?: emptyList()
    }
}