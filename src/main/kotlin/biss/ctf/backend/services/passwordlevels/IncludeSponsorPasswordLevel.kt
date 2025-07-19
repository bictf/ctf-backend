package biss.ctf.backend.services.passwordlevels

class IncludeSponsorPasswordLevel: PasswordGameLevel {
    companion object {
        private const val SPONSOR = "olga"
    }
    override fun getLevelDescription(): String {
        return "Password must include the sponsor of the biss"
    }

    override fun getLevelHint(): String {
        return "Is she the owner? or just a random russian woman..."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(SPONSOR)
    }
}