package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(Ordered.HIGHEST_PRECEDENCE + 3)
@Component
class BestHouseInHogwartsPasswordLevel: PasswordGameLevel {
    companion object {
        private const val HOUSE = "hufflepuff"
    }
    override fun getLevelDescription(): String {
        return "Password must include the best house in Hogwarts."
    }

    override fun getLevelHint(): String {
        return "Who's the real best? the real real you know."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(HOUSE)
    }
}