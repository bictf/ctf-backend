package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class PalindromePasswordLevel: PasswordGameLevel {
    override fun getLevelDescription(): String {
        return "Password must not contain any sequence that is a palindrome," +
                "however, one character may appear twice in a row."
    }

    override fun getLevelHint(): String {
        return "abcd1234"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        val length = password.length
        var allowed_repeated_character: String? = null

        // this is checking if there are two repeating characters, and if so are they the allowed character
        for (start in 0 until (length - 2)) {
            val substring = password.substring(start, start + 2)
            if (isPalindrome(substring)) {
                if (allowed_repeated_character == null) {
                    allowed_repeated_character = substring.toCharArray()[0].toString()
                }
                if (substring != "$allowed_repeated_character$allowed_repeated_character") {
                    return false
                }
            }
        }

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