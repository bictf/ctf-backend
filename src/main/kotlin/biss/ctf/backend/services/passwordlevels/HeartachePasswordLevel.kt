package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
class HeartachePasswordLevel: PasswordGameLevel {
    companion object {
        private const val KEY_WORD = "heartache"
    }
    override fun getLevelDescription(): String {
        return "Password must contain the 44th word of our favorite song!"
    }

    override fun getLevelHint(): String {
        return "Believe, when I say..."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(KEY_WORD)
    }
}