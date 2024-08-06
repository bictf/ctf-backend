package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class StairCountLevel: PasswordGameLevel {
    companion object {
        private const val STAIR_COUNT = "71"
    }
    override fun getLevelDescription(): String {
        return "Password must include the amount of stairs in building A."
    }

    override fun getLevelHint(): String {
        return "Go count!"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.contains(STAIR_COUNT)
    }
}