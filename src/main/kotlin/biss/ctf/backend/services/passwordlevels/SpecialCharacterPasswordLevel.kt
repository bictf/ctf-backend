package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 2)
class SpecialCharacterPasswordLevel: PasswordGameLevel {
    companion object {
        private val specialCharacters = arrayOf(
            "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "-", "_", "=", "+",
            "[", "]", "{", "}", "|", "\\", ":", ";", "\"", "'", "<", ">", ",", ".", "?", "/"
        )
    }
    override fun getLevelDescription(): String {
        return "Password must include special characters"
    }

    override fun getLevelHint(): String {
        return "\$p3c14L"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        for (character in specialCharacters) {
            if (password.contains(character)) {
                return true
            }
        }
        return false
    }
}