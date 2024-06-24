package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class DifferentLettersInARow: PasswordGameLevel {
    fun countDifferentRepeatedChars(input: String): Int {
        if (input.isEmpty()) return 0

        val seenChars = mutableSetOf<Char>()
        var count = 0

        for (i in 0 until input.length - 1) {
            if (input[i] == input[i + 1] && input[i] !in seenChars) {
                count++
                seenChars.add(input[i])
            }
        }

        return count
    }
    override fun getLevelDescription(): String {
        return "Only one letter can appear more than once in a row"
    }

    override fun getLevelHint(): String {
        return "if more then one letter is appearing more then once, delete it :)"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return countDifferentRepeatedChars(password) == 1
    }
}