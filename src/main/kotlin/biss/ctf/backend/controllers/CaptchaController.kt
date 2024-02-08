package biss.ctf.backend.controllers

import biss.ctf.backend.services.captchas.CaptchaImageService
import biss.ctf.backend.services.captchas.CaptchaQuestionService
import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/captcha")
class CaptchaController(
    @Value("\${captcha.init.should_block}")
    var shouldBlockCaptcha: Boolean,
    val captchaImageService: CaptchaImageService,
    val captchaQuestionService: CaptchaQuestionService
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

    @GetMapping("/questions")
    fun getAllCaptchaQuestions() = captchaQuestionService.getAllCaptcha()

    @GetMapping("/pictures")
    @ResponseBody
    fun getAllCaptchaPictures(): ResponseEntity<List<CaptchaImageService.ImageData>> {
        return ResponseEntity(captchaImageService.getAllCaptcha(), HttpStatus.OK)
    }

    @GetMapping("/pictures/paged")
    @ResponseBody
    fun getSomePictures(@RequestParam amount: Int): ResponseEntity<List<CaptchaImageService.ImageData>> {
        logger.info { "Retrieving $amount CAPTCHA${if (amount > 1) "s" else ""}" }
        val captchas = ArrayList<CaptchaImageService.ImageDataDTO>()
        for (i in 0 until amount) {
            captchas.add(captchaImageService.getNextCaptcha())
        }
        return ResponseEntity(captchas, HttpStatus.OK)
    }

}