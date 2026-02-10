package biss.ctf.backend.services.passwordlevels

class BestUnitPasswordLevel : PasswordGameLevel {
    companion object {
        private const val MATZOV = "matzov"
        private const val GENESIS = "genesis"
    }

    /**
     * The description of the level.
     * Used when explaining to the user what the requirement for the password is.
     * For instance, a requirement to have a capital letter would have a description of "password must contain a capital letter"
     */
    override fun getLevelDescription(): String {
        return "What are the best units in the IDF?"
    }

    /**
     * The hint for the level.
     * Used when the level has an explainable example.
     * For instance, a requirement to have a capital letter would have a hint of "Helloworld", as it contains a capital letter.
     */
    override fun getLevelHint(): String {
        return ""
    }

    /**
     * A function to check whether the password answers the requirements for the level.
     * For instance, a requirement to have a capital letter would have a function to check if any of the letters are capitalized.
     */
    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(MATZOV) &&
                password.lowercase().contains(GENESIS)
    }
}
