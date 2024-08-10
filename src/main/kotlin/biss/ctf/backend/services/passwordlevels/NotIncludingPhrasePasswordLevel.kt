package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 19)
class NotIncludingPhrasePasswordLevel: PasswordGameLevel {
    companion object {
        private const val STATE = "y8"
    }
    override fun getLevelDescription(): String {
        return "Password must not include the phrase \"y8\""
    }

    override fun getLevelHint(): String {
        return "So, How Long Until u Break?"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return !password.lowercase().contains(STATE)
    }
}