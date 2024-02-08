package biss.ctf.backend.services.captchas

import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Service
import java.io.File

@Service
class CaptchaImageService(
    val captchaImages: List<ImageData>
) : CaptchaService {
    var currentIndex = 0

    /**
     * A data class to hold picture data for the picture CAPTCHAs
     */
    data class ImageData(
        val image: File, val imageName: String
    ) {
        fun toDTO() = ImageDataDTO(image.inputStream().readAllBytes(), imageName)
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

    @Caching
    override fun getAllCaptcha(): List<Any> = captchaImages.map { it.toDTO() }

    /**
     * Cycles over CAPTCHAs and returns the next one.
     */
    fun getNextCaptcha(): ImageDataDTO {
        val captcha = captchaImages[currentIndex].toDTO()
        currentIndex += 1
        if (currentIndex >= captchaImages.size) {
            currentIndex = 0
        }
        return captcha
    }
}