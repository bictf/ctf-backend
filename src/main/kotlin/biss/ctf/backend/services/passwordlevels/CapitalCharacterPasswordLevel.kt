package biss.ctf.backend.services.passwordlevels

class CapitalCharacterPasswordLevel: PasswordGameLevel {

    override fun getLevelDescription(): String {
        return "Password must include capital character"
    }

    override fun getLevelHint(): String {
        return "Ggez1234"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        for (c in password) {
            if (c.isUpperCase()) {
                return true
            }
        }
        return false
    }
}