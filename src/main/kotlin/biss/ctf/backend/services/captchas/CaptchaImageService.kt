package biss.ctf.backend.services.captchas

import biss.ctf.backend.entities.ImageData
import org.springframework.stereotype.Service

@Service
class CaptchaImageService(
    val captchaImages: List<ImageData>
) {
    var currentIndex = 0

    fun getAllCaptcha() = captchaImages

    /**
     * Retrieves all the image names.
     */
    fun getAllCaptchaNames() = captchaImages.map { it.imageName }

    /**
     * Cycles over CAPTCHAs and returns the next one.
     */
    fun getNextCaptcha(): ImageData {
        val captcha = captchaImages[currentIndex]
        currentIndex += 1
        if (currentIndex >= captchaImages.size) {
            currentIndex = 0
        }
        return captcha
    }
}