package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class StaffHeightPasswordLevel: PasswordGameLevel {
    companion object {
        private const val STAFF_TOTAL_HEIGHT = "5.10m"
    }
    override fun getLevelDescription(): String {
        return "Password must include the sum of heights of the Ramagim and the Makas."
    }

    override fun getLevelHint(): String {
        return "Format is M.CMm."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.contains(STAFF_TOTAL_HEIGHT)
    }
}