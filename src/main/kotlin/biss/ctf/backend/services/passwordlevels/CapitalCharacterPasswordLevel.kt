package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class CapitalCharacterPasswordLevel: PasswordGameLevel {

    override fun getLevelDescription(): String {
        return "Password must include capital character"
    }

    override fun getLevelHint(): String {
        return "seriously?"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        for (c in password) {
            if (c.isUpperCase()) {
                return true
            }
        }
        return false
    }
}