package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 18)
class RomanNumeralPasswordLevel : PasswordGameLevel {
    companion object {
        const val ROMAN_NUMERAL_DIVISOR = 35
        val ROMAN_NUMERAL_VALUES = mapOf(
            "I" to 1, "II" to 2, "III" to 3, "IV" to 4, "V" to 5, "VI" to 6, "VII" to 7,
            "VIII" to 8, "IX" to 9, "X" to 10, "XX" to 20, "XXX" to 30, "XL" to 40,
            "L" to 50, "LX" to 60, "LXX" to 70, "LXXX" to 80, "XC" to 90, "C" to 100,
            "CC" to 200, "CCC" to 300, "CD" to 400, "D" to 500, "DC" to 600, "DCC" to 700,
            "DCCC" to 800, "CM" to 900, "M" to 1000, "MM" to 2000, "MMM" to 3000
        )
    }

    override fun getLevelDescription(): String {
        return "Roman numerals sum must be divisible by $ROMAN_NUMERAL_DIVISOR."
    }

    override fun getLevelHint(): String {
        return "Go check wikipedia or something"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        var sum = 0
        var passwordToSearch = String(password.toCharArray())
        ROMAN_NUMERAL_VALUES.entries.sortedWith(compareBy { (key, _) -> -key.length })
            .forEach { (id, value) ->
                while (id in passwordToSearch) {
                    passwordToSearch = passwordToSearch.replaceFirst(id, "")
                    sum += value
                }
            }
        return sum % ROMAN_NUMERAL_DIVISOR == 0
    }
}