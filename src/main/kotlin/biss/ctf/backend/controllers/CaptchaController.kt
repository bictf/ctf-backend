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
    @Value("\${captcha.init.should_block}")
    var shouldBlockCaptcha: Boolean,
    val captchaImageService: CaptchaImageService,
    val captchaQuestionService: CaptchaQuestionService,
    val textCaptchas: List<TextCaptcha>
) {

    companion object {
        private val logger = KotlinLogging.logger(CaptchaController::class.java.name)
    }

    @GetMapping("/is-blocked")
    fun canSkipCaptcha(): ResponseEntity<Boolean> {
        return ResponseEntity(shouldBlockCaptcha, HttpStatus.OK)
    }

    @PostMapping("/cG9zdCBoZXJlIHRvIGZsaXAgd2hldGhlciB0aGUgY2FwdGNoYSBzaG91bGQgcGFzcyBvciBub3Q")
    fun flipCaptcha(): ResponseEntity<Boolean> {
        shouldBlockCaptcha = !shouldBlockCaptcha
        return ResponseEntity(shouldBlockCaptcha, HttpStatus.OK)
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
        val captcha = captchaImageService.getAllCaptcha().find {
            it.imageName == name
        }
        if (captcha == null) {
            return ResponseEntity(HttpStatus.NOT_FOUND)
        }
        val imageFile = captcha.image
        return ResponseEntity(imageFile.readBytes(), HttpStatus.OK)
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
