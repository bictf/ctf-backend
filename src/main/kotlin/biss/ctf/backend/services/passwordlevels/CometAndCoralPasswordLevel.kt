package biss.ctf.backend.services.passwordlevels

class CometAndCoralPasswordLevel: PasswordGameLevel {
    companion object {
        private const val COMET = "comet"
        private const val CORAL = "coral"
    }
    override fun getLevelDescription(): String {
        return "Password must contain the names of the website creators."
    }

    override fun getLevelHint(): String {
        return "Look just under your nose..."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        if (password.lowercase().contains(COMET) &&
            password.lowercase().contains(CORAL)) {
            return true
        }
        return false
    }
}