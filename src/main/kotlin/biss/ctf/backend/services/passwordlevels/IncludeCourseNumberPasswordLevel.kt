package biss.ctf.backend.services.passwordlevels

class IncludeCourseNumberPasswordLevel: PasswordGameLevel {
    companion object {
        private const val COURSE_NUM = "19"
    }
    override fun getLevelDescription(): String {
        return "Password must include the biss number"
    }

    override fun getLevelHint(): String {
        return "Sometimes Hidden Letters Undergo Blending."
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return password.lowercase().contains(COURSE_NUM)
    }
}