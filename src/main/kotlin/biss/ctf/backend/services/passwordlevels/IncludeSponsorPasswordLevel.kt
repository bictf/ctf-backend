package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 9)
class IncludeSponsorPasswordLevel: PasswordGameLevel {
    companion object {
        private const val SPONSOR = "olga"
    }
    override fun getLevelDescription(): String {
        return "Password must include the sponsor of the biss"
    }

    override fun getLevelHint(): String {
        return "Is she the owner? or just a random russian woman..."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(SPONSOR)
    }
}