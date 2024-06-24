package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class HexMirrorPasswordLevel : PasswordGameLevel {
    companion object {
        private val MIRROR_PAIRS = mapOf(
            '0' to 'f', 'f' to '0',
            '1' to 'e', 'e' to '1',
            '2' to 'd', 'd' to '2',
            '3' to 'c', 'c' to '3',
            '4' to 'b', 'b' to '4',
            '5' to 'a', 'a' to '5',
            '6' to '9', '9' to '6',
            '7' to '8', '8' to '7'
        )
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
        return MIRROR_PAIRS[firstChar] == lastChar
    }
}
