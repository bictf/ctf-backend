package biss.ctf.backend.services.captchas

import org.springframework.stereotype.Service
import java.nio.file.Path

@Service
class CaptchaImageService(
    val captchaImages: List<ImageData>
) {
    var currentIndex = 0

    /**
     * A data class to hold picture data for the picture CAPTCHAs
     */
    data class ImageData(
        val image: Path, val imageName: String
    )

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