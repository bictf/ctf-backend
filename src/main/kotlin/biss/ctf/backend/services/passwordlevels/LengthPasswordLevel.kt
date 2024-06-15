package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class LengthPasswordLevel: PasswordGameLevel {
    companion object {
        private const val MINIMUM_PASSWORD_LENGTH = 8
    }
    override fun getLevelDescription(): String {
        return "Password must be at least $MINIMUM_PASSWORD_LENGTH characters long"
    }

    override fun getLevelHint(): String {
        return "Gg123456"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.length >= MINIMUM_PASSWORD_LENGTH
    }
}
