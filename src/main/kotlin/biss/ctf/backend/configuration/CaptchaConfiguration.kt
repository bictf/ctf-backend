package biss.ctf.backend.configuration

import biss.ctf.backend.services.captchas.CaptchaQuestionService
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties(prefix = "captcha.data")
@PropertySource("classpath:captcha.properties")
class CaptchaConfiguration {
    lateinit var captchaQuestions: List<CaptchaQuestionService.QuestionData>

    @Bean
    fun getAllCaptchaQuestions() = captchaQuestions
}