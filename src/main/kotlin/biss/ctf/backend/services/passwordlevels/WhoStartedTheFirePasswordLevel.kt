package biss.ctf.backend.services.passwordlevels

class WhoStartedTheFirePasswordLevel : PasswordGameLevel {
    companion object {
        const val FIRE_STARTER = "Elmo"
    }

    override fun getLevelDescription(): String {
        return "Who started the fire?"
    }

    override fun getLevelHint(): String {
        return "Open Sesame"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.contains(FIRE_STARTER, ignoreCase = true)
    }
}