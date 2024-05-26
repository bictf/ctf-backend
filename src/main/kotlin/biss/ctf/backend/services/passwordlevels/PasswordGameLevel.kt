package biss.ctf.backend.services.passwordlevels

interface PasswordGameLevel {
    /**
     * The description of the level.
     * Used when explaining to the user what the requirement for the password is.
     * For instance, a requirement to have a capital letter would have a description of "password must contain a capital letter"
     */
    fun getLevelDescription(): String

    /**
     * The hint for the level.
     * Used when the level has an explainable example.
     * For instance, a requirement to have a capital letter would have a hint of "Helloworld", as it contains a capital letter.
     */
    fun getLevelHint(): String

    /**
     * A function to check whether the password answers the requirements for the level.
     * For instance, a requirement to have a capital letter would have a function to check if any of the letters are capitalized.
     */
    fun doesAnswerLevel(password: String): Boolean
}