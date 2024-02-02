package biss.ctf.backend.controllers

import biss.ctf.backend.services.captchas.CaptchaImageService
import biss.ctf.backend.services.captchas.CaptchaQuestionService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/captcha")
class CaptchaController(
        @Value("\${captcha.init.should_block}")
        var shouldBlockCaptcha: Boolean,
        val captchaImageService: CaptchaImageService,
        val captchaQuestionService: CaptchaQuestionService
) {

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
    fun getAllCaptchaQuestions() {
        TODO("Not yet implemented")
    }

    @GetMapping("/pictures")
    fun getAllCaptchaPictures() {
        TODO("Not yet implemented")
    }
}