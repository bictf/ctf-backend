package biss.ctf.backend.configuration

import biss.ctf.backend.entities.FileEntity
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
class IntelligenceConfiguration {
    companion object {
        val logger = KotlinLogging.logger(IntelligenceConfiguration::class.java.name)
    }

    @Bean
    @Qualifier("intelligenceFiles")
    fun getIntelligenceFiles(
        @Value("\${intelligence.root_folder}") intelligenceFilesRoot: String
    ): Sequence<FileEntity> {
        val intelligenceFolder = File(intelligenceFilesRoot)
        if (!intelligenceFolder.isDirectory) {
            throw IllegalArgumentException("$intelligenceFilesRoot is not  a directory!")
        }
        logger.info("Loading intelligence files into memory!")
        return intelligenceFolder.walk().map {
            FileEntity(it.name, it.readText(), false, it)
        }
    }

    @Bean
    @Qualifier("binaryFiles")
    fun getBinaryFiles(
        @Value("\${binaries.root_folder}") intelligenceFilesRoot: String
    ): Sequence<FileEntity> {
        val binariesFolder = File(intelligenceFilesRoot)
        if (!binariesFolder.isDirectory) {
            throw IllegalArgumentException("$intelligenceFilesRoot is not  a directory!")
        }
        logger.info("Loading binary file references into memory!")
        return binariesFolder.walk().map {
            FileEntity(it.name, "?????????", true, it)
        }
    }
}