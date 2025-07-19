package biss.ctf.backend.services.passwordlevels

class MonthPasswordLevel: PasswordGameLevel {
    companion object {
        private val MONTHS_IN_YEAR = arrayOf(
            "january", "february", "march", "april", "may", "june",
            "july", "august", "september", "october", "november", "december"
        )
    }
    override fun getLevelDescription(): String {
        return "Password must contain the name of a month"
    }

    override fun getLevelHint(): String {
        return "may_I_have_some_help"
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