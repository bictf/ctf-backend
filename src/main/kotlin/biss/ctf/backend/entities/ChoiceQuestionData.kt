package biss.ctf.backend.entities

/**
 * A data class for the question based CAPTCHAs.
 * @param title The title of the question.
 * @param answers A list of answers to display
 * @param correctAnswer The index of the correct answer
 */
data class ChoiceQuestionData(
    val title: String,
    val answers: List<String>,
    val correctAnswer: Int
)

