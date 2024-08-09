package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class StaffHeightPasswordLevel : PasswordGameLevel {
    companion object {
        private const val STAFF_TOTAL_HEIGHT_IN_CM = 1906
        private const val ALLOWED_ERROR_MARGIN_IN_CM = 10
    }

    override fun getLevelDescription(): String {
        return "Password must include the sum of heights of the Lahav commanders (not including Ramagim and Makas) in cm."
    }

    override fun getLevelHint(): String {
        return ""
    }

    override fun doesAnswerLevel(password: String): Boolean {
        val numberSequences = getNumberSequences(password)

        return numberSequences.any {
            it in (STAFF_TOTAL_HEIGHT_IN_CM - ALLOWED_ERROR_MARGIN_IN_CM)..(STAFF_TOTAL_HEIGHT_IN_CM + ALLOWED_ERROR_MARGIN_IN_CM)
        }
    }

    fun getNumberSequences(text: String): List<Int> {
        val getNumberSequencesPattern = "\\d+"

        return Regex(getNumberSequencesPattern).findAll(text).map { it.value.toInt() }.toList()
    }
}