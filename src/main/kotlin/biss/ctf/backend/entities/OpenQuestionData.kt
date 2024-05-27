package biss.ctf.backend.entities

/**
 * A data class for the question based CAPTCHAs.
 * @param title The title of the question.
 * @param answer The answer for the question
 */
data class OpenQuestionData(
    val title: String,
    val answer: String,
    val image: String? = null
)

