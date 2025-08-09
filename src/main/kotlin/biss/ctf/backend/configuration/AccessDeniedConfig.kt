package biss.ctf.backend.configuration

import biss.ctf.backend.objects.apiObjects.Megama
import jakarta.validation.constraints.AssertTrue
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated


@Configuration
@ConfigurationProperties(prefix = "access-denied")
@Validated
class AccessDeniedConfig {

    companion object {
        val BRACKET_REGEX = Regex(".*\\[[^\\]]+\\].*")
    }

    lateinit var defaultMessage: String
    lateinit var noSuchUserMessage: String
    lateinit var notLoggedInMessage: String
    lateinit var defaultCookieMessage: String
    lateinit var megamaCookieMessages: Map<Megama, String>

    @AssertTrue(message = "Messages weren't configured properly - did not replace properties in '[]'")
    fun validateCookieMessages(
    ): Boolean = megamaCookieMessages.values.all { message ->
        !BRACKET_REGEX.matches(message)
    }

}