package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 7)
class StrengthPasswordLevel : PasswordGameLevel {
    companion object {
        val STRENGTH_EMOJIS: List<String> = listOf("💪", "🦾", "🏋️", "🏋️‍♀️", "🏋️‍♂️")
    }

    override fun getLevelDescription(): String {
        return "Password not strong enough!"
    }

    override fun getLevelHint(): String {
        return "Think harder! ${STRENGTH_EMOJIS.random()}"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return STRENGTH_EMOJIS.any { it in password }
    }
}