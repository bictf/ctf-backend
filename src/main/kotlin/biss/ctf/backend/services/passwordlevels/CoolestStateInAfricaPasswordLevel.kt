package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class CoolestStateInAfricaPasswordLevel: PasswordGameLevel {
    companion object {
        private const val STATE = "chad"
    }
    override fun getLevelDescription(): String {
        return "Password must include the coolest state in Africa"
    }

    override fun getLevelHint(): String {
        return "you can figure this out."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(STATE)
    }
}