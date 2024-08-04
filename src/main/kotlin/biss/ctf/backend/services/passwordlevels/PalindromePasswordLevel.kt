package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class PalindromePasswordLevel: PasswordGameLevel {
    override fun getLevelDescription(): String {
        return "Password must not contain any sequence that is a palindrome of length 3 or more."
    }

    override fun getLevelHint(): String {
        return "aabb1234"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        val length = password.length

        //this is checking all substrings of length 3 and more are palindromes
        for (start in 0 until (length - 3)) {
            for (end in (start + 3) until length) {
                val substring = password.substring(start, end)
                if (isPalindrome(substring)) {
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