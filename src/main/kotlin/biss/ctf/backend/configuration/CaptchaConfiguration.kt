package biss.ctf.backend.configuration

import biss.ctf.backend.entities.ChoiceQuestionData
import biss.ctf.backend.entities.ImageData
import biss.ctf.backend.entities.OpenQuestionData
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
    lateinit var choiceCaptchaQuestions: List<ChoiceQuestionData>
    lateinit var openCaptchaQuestions: List<OpenQuestionData>

    lateinit var imageFolder: String

    companion object {
        val logger = KotlinLogging.logger(CaptchaConfiguration::class.java.name)
    }

    @Bean
    fun getAllChoiceCaptchaQuestions() = choiceCaptchaQuestions

    @Bean
    fun getAllOpenCaptchaQuestions() = openCaptchaQuestions

    @Bean
    fun getCaptchaImages(): List<ImageData> {
        val imagesFolder = Paths.get(imageFolder)
        if (!imagesFolder.exists() || !imagesFolder.isDirectory()) {
            throw FileNotFoundException("'$imageFolder' does not exist or is not a directory!")
        }

        logger.info("Loading picture references into memory!")
        return imagesFolder.toFile().listFiles()?.map {
            ImageData(it.toPath(), it.name)
        } ?: emptyList()
    }
}