package biss.ctf.backend.services.passwordlevels

class StrengthPasswordLevel : PasswordGameLevel {
    companion object {
        val STRENGTH_EMOJIS: List<String> = listOf("💪", "🦾", "🏋️", "🏋️‍♀️", "🏋️‍♂️")
    }

    override fun getLevelDescription(): String {
        return "Password not strong enough!"
    }

    override fun getLevelHint(): String {
        return "Think harder! ${STRENGTH_EMOJIS.random()}"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return STRENGTH_EMOJIS.any { it in password }
    }
}