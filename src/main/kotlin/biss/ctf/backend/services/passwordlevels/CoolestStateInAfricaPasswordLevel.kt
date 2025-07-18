package biss.ctf.backend.services.passwordlevels

class CoolestStateInAfricaPasswordLevel : PasswordGameLevel {
    companion object {
        private const val STATE = "chad"
    }

    override fun getLevelDescription(): String {
        return "Password must include the coolest state in Africa"
    }

    override fun getLevelHint(): String {
        return "Who is cool?"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(STATE)
    }
}