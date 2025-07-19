package biss.ctf.backend.services.passwordlevels

class WebsiteRatingPasswordLevel : PasswordGameLevel {
    companion object {
        const val FIVE_STAR_RATING: String = "⭐⭐⭐⭐⭐"
        const val FUCKED_UP_FIVE_STAR_RATING: String = "⭐\uFE0F⭐\uFE0F⭐\uFE0F⭐\uFE0F⭐\uFE0F"
    }

    override fun getLevelDescription(): String {
        return "Password must include website rating ⭐"
    }

    override fun getLevelHint(): String {
        return "Try finding some ⭐s"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.contains(FIVE_STAR_RATING) || password.contains(FUCKED_UP_FIVE_STAR_RATING)
    }
}