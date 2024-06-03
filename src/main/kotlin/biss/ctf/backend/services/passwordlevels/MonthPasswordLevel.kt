package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class MonthPasswordLevel: PasswordGameLevel {
    companion object {
        private val MONTHS_IN_YEAR = arrayOf(
            "january", "february", "march", "april", "may", "june",
            "july", "august", "september", "october", "november", "december"
        )
    }
    override fun getLevelDescription(): String {
        return "Password must a month in it"
    }

    override fun getLevelHint(): String {
        return "no hint."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        for (month in MONTHS_IN_YEAR) {
            if (password.lowercase().contains(month)) {
                return true
            }
        }
        return false
    }
}