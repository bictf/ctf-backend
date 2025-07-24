package biss.ctf.backend.services.passwordlevels

class LengthPasswordLevel(
    private val minimumPasswordLength: Int,
): PasswordGameLevel {

    override fun getLevelDescription(): String {
        return "Password must be at least $minimumPasswordLength characters long"
    }

    override fun getLevelHint(): String {
        return "Gg123456"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.length >= minimumPasswordLength
    }
}
