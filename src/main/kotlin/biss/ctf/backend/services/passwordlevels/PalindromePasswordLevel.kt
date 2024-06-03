package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class PalindromePasswordLevel: PasswordGameLevel {
    override fun getLevelDescription(): String {
        return "Password must not contain any sequence that is a palindrom." +
                "Except for a certain letter that can appear only twice in a row."
    }

    override fun getLevelHint(): String {
        return "abcd1234"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        val length = password.length
        for (start in 0 until length) {
            for (end in (start + 2)..length) {
                val substring = password.substring(start, end)
                if (isPalindrome(substring) && substring != "ff") {
                    return false
                }
            }
        }
        return true
    }


    fun isPalindrome(s: String): Boolean {
        return s == s.reversed()
    }
}