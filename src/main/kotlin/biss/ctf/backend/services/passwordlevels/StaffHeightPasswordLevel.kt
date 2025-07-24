package biss.ctf.backend.services.passwordlevels

class StaffHeightPasswordLevel(
    private val staffTotalHeightInCm: Int,
) : PasswordGameLevel {
    companion object {
        private const val ALLOWED_ERROR_MARGIN_IN_CM = 10
    }

    override fun getLevelDescription(): String {
        return "Password must include the sum of heights of all the commanders (not including Ramagim and Makas) in cm."
    }

    override fun getLevelHint(): String {
        return ""
    }

    override fun doesAnswerLevel(password: String): Boolean {
        val numberSequences = getNumberSequences(password)

        return numberSequences.any {
            it in (staffTotalHeightInCm - ALLOWED_ERROR_MARGIN_IN_CM)..(staffTotalHeightInCm + ALLOWED_ERROR_MARGIN_IN_CM)
        }
    }

    fun getNumberSequences(text: String): List<Int> {
        val getNumberSequencesPattern = "\\d+"

        return Regex(getNumberSequencesPattern).findAll(text).map { it.value.toInt() }.toList()
    }
}