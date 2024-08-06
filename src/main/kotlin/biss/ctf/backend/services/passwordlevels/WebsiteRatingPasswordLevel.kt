package biss.ctf.backend.services.passwordlevels

import org.springframework.stereotype.Component

@Component
class WebsiteRatingPasswordLevel : PasswordGameLevel {
    companion object {
        const val FIVE_STAR_RATING: String = "⭐⭐⭐⭐⭐"
    }

    override fun getLevelDescription(): String {
        return "Password must include website rating ⭐"
    }

    override fun getLevelHint(): String {
        return "Try finding some ⭐s"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.contains(FIVE_STAR_RATING)
    }
}