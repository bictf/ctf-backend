package biss.ctf.backend.services.passwordlevels

class LeapYearPasswordLevel : PasswordGameLevel {
    override fun getLevelDescription(): String {
        return "Password must include the a leap year"
    }

    override fun getLevelHint(): String {
        return ""
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return getNumberSequences(password).any{isLeapYear(it.toInt())}
    }

    fun getNumberSequences(text: String): List<String> {
        val getNumberSequencesPattern = "\\d+"

        return Regex(getNumberSequencesPattern).findAll(text).map {it.value}.toList()
    }

    fun isLeapYear(year: Int): Boolean {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
    }
}

