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
    ) {
        fun toDTO() = ImageDataDTO(image.toFile().inputStream().readAllBytes(), imageName)
    }

    /**
     * A data class used for the actual picture transfers.
     * **NOTE:** INTELLIJ was angry, so I added the [equals] and [hashCode] functions
     */
    data class ImageDataDTO(
        val image: ByteArray, val imageName: String
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as ImageDataDTO

            if (!image.contentEquals(other.image)) return false
            if (imageName != other.imageName) return false

            return true
        }

        override fun hashCode(): Int {
            var result = image.contentHashCode()
            result = 31 * result + imageName.hashCode()
            return result
        }
    }

    fun getAllCaptcha() = captchaImages

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