package biss.ctf.backend.services.passwordlevels

class DigitsSumDivisibleByBissNumberPasswordLevel(
    private val bissNumber: Int,
): PasswordGameLevel {

    override fun getLevelDescription(): String {
        return "Sum of digits in password must be divisible by $bissNumber."
    }

    override fun getLevelHint(): String {
        return "So simple yet so hard when you're a biss magician..."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        // Sum the digits in the password
        val sumOfDigits = password.filter { it.isDigit() }
            .map { Character.getNumericValue(it) }
            .sum()
        // Check if the sum is divisible by biss number
        return sumOfDigits % bissNumber == 0
    }
}
