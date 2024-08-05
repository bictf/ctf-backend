package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class LeapYearPasswordLevel: PasswordGameLevel {
    override fun getLevelDescription(): String {
        return "Password must include the a leap year"
    }

    override fun getLevelHint(): String {
        return ""
    }

    override fun doesAnswerLevel(password: String): Boolean {
        for (start in 0 until (password.length - 1)) {
            for (end in (start + 1) until password.length) {
                val potentialYear = password.substring(start, end).toIntOrNull()
                if (isLeapYear(potentialYear)) {
                    return false
                }
            }
        }

        return true
    }
}

fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}