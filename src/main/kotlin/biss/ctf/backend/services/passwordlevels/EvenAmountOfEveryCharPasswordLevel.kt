package biss.ctf.backend.services.passwordlevels

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.HIGHEST_PRECEDENCE + 16)
class EvenAmountOfEveryCharPasswordLevel : PasswordGameLevel {
    override fun getLevelDescription(): String {
        return "Password must have an even amount of every character used."
    }

    override fun getLevelHint(): String {
        return "Sometimes Having Lots of Unique Bytes is key."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        // Check if every character in the password has an even count
        val charCounts = password.groupingBy { it }.eachCount()
        return charCounts.values.all { it % 2 == 0 }
    }
}
