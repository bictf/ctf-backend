package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 13)
class WhoStartedTheFirePasswordLevel : PasswordGameLevel {
    companion object {
        const val FIRE_STARTER = "Elmo"
    }

    override fun getLevelDescription(): String {
        return "Who started the fire?"
    }

    override fun getLevelHint(): String {
        return "Open Sesame"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.contains(FIRE_STARTER, ignoreCase = true)
    }
}