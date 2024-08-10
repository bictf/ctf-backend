package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 14)
class ShlubPasswordLevel: PasswordGameLevel {
    override fun getLevelDescription(): String {
        return "Password must include the gematric value of the word SHLUB"
    }

    override fun getLevelHint(): String {
        return "May the shlub be with you."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.contains("338")
    }
}
