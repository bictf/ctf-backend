package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class HexMirrorPasswordLevel : PasswordGameLevel {
    companion object {
        const val HEX_BASE = 16
    }

    override fun getLevelDescription(): String {
        return "Password must start and end with valid hexadecimal characters that form mirror pairs."
    }

    override fun getLevelHint(): String {
        return "Some Hexes Look Unusual, But."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        if (password.isEmpty()) return false

        val firstChar = password.first().lowercaseChar()
        val lastChar = password.last().lowercaseChar()

        return try {
            firstChar.getMirrorInBase(HEX_BASE) == lastChar
        } catch (e: Exception) {
            // Happens when the character is not legal for this base
            false
        }
        
    }

    fun Char.getMirrorInBase(base: Int = 10): Char? {
        val base10Value = this.digitToInt(base)
        val mirrorValue = (base - 1) - base10Value
        return mirrorValue.digitToChar(base)
    }
}
