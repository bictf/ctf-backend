package biss.ctf.backend.services.passwordlevels

class StairsInBuildingAPasswordLevel : PasswordGameLevel {
    companion object {
        private const val STAIR_COUNT = 76
    }

    override fun getLevelDescription(): String {
        return "Password must include the stair count in building A"
    }

    override fun getLevelHint(): String {
        return "Yeah you're not getting help with this one"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(STAIR_COUNT.toString())
    }
}