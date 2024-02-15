package biss.ctf.backend.controllers

import biss.ctf.backend.entities.TextCaptcha
import biss.ctf.backend.services.captchas.CaptchaImageService
import biss.ctf.backend.services.captchas.CaptchaQuestionService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.io.path.nameWithoutExtension
import kotlin.io.path.readBytes


@RestController
@RequestMapping("/captcha")
class CaptchaController(
    @Value("\${captcha.init.should_skip}")
    var shouldSkip: Boolean,
    val captchaImageService: CaptchaImageService,
    val captchaQuestionService: CaptchaQuestionService,
    val textCaptchas: List<TextCaptcha>
) {

    companion object {
        private val logger = KotlinLogging.logger(CaptchaController::class.java.name)
    }

    @GetMapping("/can_skip")
    fun canSkipCaptcha(): ResponseEntity<Boolean> {
        return ResponseEntity(shouldSkip, HttpStatus.OK)
    }

    @PostMapping("/cG9zdCBoZXJlIHRvIGZsaXAgd2hldGhlciB0aGUgY2FwdGNoYSBzaG91bGQgcGFzcyBvciBub3Q")
    fun flipCaptcha(): ResponseEntity<Boolean> {
        shouldSkip = !shouldSkip
        return ResponseEntity(shouldSkip, HttpStatus.OK)
    }

    @GetMapping("/questions/choice")
    fun getAllChoiceCaptchaQuestions() = captchaQuestionService.getAllChoiceCaptcha()

    @GetMapping("/questions/open")
    fun getAllOpenCaptchaQuestions() = captchaQuestionService.getAllOpenCaptcha()


    @GetMapping("/pictures")
    @ResponseBody
    fun getSomePictures(): ResponseEntity<List<String>> {
        logger.info { "Retrieving all CAPTCHAs" }
        return ResponseEntity(captchaImageService.getAllCaptchaNames(), HttpStatus.OK)
    }

    @GetMapping("/pictures/by_name")
    @ResponseBody
    fun getPicture(@RequestParam name: String): ResponseEntity<ByteArray> {
        val regularCaptcha = captchaImageService.getAllCaptcha().find { it.imageName == name }
        val textRecognitionCaptcha = textCaptchas.find { it.image.nameWithoutExtension == name }

        return if (regularCaptcha != null) {
            ResponseEntity(regularCaptcha.image.readBytes(), HttpStatus.OK)
        } else if (textRecognitionCaptcha != null) {
            ResponseEntity(textRecognitionCaptcha.image.readBytes(), HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @GetMapping("/text_captchas")
    @ResponseBody
    fun getTextCaptcha(): ResponseEntity<List<String>> {
        return ResponseEntity(textCaptchas.map { it.image.nameWithoutExtension }, HttpStatus.OK)
    }

    @GetMapping("/text_captchas/by_name")
    @ResponseBody
    fun getTextCaptcha(@RequestParam captchaName: String): ResponseEntity<ByteArray?> {
        return ResponseEntity(
            textCaptchas.find { it.image.nameWithoutExtension == captchaName }?.image?.readBytes(),
            HttpStatus.OK
        )
    }
}
