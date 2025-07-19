package biss.ctf.backend.services.passwordlevels

class PaiShoSpecialFlowerPasswordLevel: PasswordGameLevel {
    companion object {
        private const val WHITE_LOTUS = "whitelotus"
        private const val ORCHID = "orchid"
    }
    override fun getLevelDescription(): String {
        return "Password must include the name of a special flower in the game of Pai Sho"
    }

    override fun getLevelHint(): String {
        return ""
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(WHITE_LOTUS) || password.lowercase().contains(ORCHID)
    }
}