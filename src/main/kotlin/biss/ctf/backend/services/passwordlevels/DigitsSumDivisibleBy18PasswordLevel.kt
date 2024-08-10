package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 15)
class DigitsSumDivisibleBy18PasswordLevel: PasswordGameLevel {
    companion object {
        private const val DIVISIBLE_BY = 18
    }
    override fun getLevelDescription(): String {
        return "Sum of digits in password must be divisible by $DIVISIBLE_BY."
    }

    override fun getLevelHint(): String {
        return "So simple yet so hard when you're a biss magician..."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        // Sum the digits in the password
        val sumOfDigits = password.filter { it.isDigit() }
            .map { Character.getNumericValue(it) }
            .sum()
        // Check if the sum is divisible by 18
        return sumOfDigits % DIVISIBLE_BY == 0
    }
}
