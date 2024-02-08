package biss.ctf.backend.configuration

import biss.ctf.backend.services.captchas.CaptchaImageService
import biss.ctf.backend.services.captchas.CaptchaQuestionService
import mu.KotlinLogging
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import java.io.FileNotFoundException
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.isDirectory

@Configuration
@ConfigurationProperties(prefix = "captcha.data")
@PropertySource("classpath:captcha.properties")
class CaptchaConfiguration {
    lateinit var captchaQuestions: List<CaptchaQuestionService.QuestionData>
    lateinit var imageFolder: String

    companion object {
        val logger = KotlinLogging.logger(CaptchaConfiguration::class.java.name)
    }

    @Bean
    fun getAllCaptchaQuestions() = captchaQuestions

    @Bean
    fun getCaptchaImages(): List<CaptchaImageService.ImageData> {
        val imagesFolder = Paths.get(imageFolder)
        if (!imagesFolder.exists() || !imagesFolder.isDirectory()) {
            throw FileNotFoundException("'$imageFolder' does not exist or is not a directory!")
        }

        logger.info("Loading picture references into memory!")
        return imagesFolder.toFile().listFiles()?.map {
            CaptchaImageService.ImageData(it.toPath(), it.name)
        } ?: emptyList()
    }
}