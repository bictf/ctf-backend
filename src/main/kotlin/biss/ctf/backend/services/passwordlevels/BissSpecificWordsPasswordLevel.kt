package biss.ctf.backend.services.passwordlevels

class BissSpecificWordsPasswordLevel(
    private val levelDescription: String,
    private val levelSpecificWords: List<String>,
): PasswordGameLevel {

    override fun getLevelDescription(): String {
        return levelDescription
    }

    override fun getLevelHint(): String {
        return ""
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return levelSpecificWords.any { password.lowercase().contains(it) }
    }
}