package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 11)
class IncludeWordPalindromePasswordLevel: PasswordGameLevel {
    companion object {
        private const val PALINDROME = "palindrome"
    }
    override fun getLevelDescription(): String {
        return "Password must include palindrome"
    }

    override fun getLevelHint(): String {
        return "not *A* palindrome"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(PALINDROME)
    }
}