package biss.ctf.backend.services.passwordlevels

class NotIncludingPhrasePasswordLevel(
    private val phrase: String,
): PasswordGameLevel {

    override fun getLevelDescription(): String {
        return "Password must not include the phrase \"$phrase\""
    }

    override fun getLevelHint(): String {
        return "So, How Long Until u Break?"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return !password.lowercase().contains(phrase)
    }
}