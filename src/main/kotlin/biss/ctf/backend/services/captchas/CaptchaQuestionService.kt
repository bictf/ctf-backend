package biss.ctf.backend.services.captchas

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
class CaptchaQuestionService(
    val questions: List<QuestionData>
) {

    /**
     * A data class for the question based CAPTCHAs.
     * @param title The title of the question.
     * @param answers A list of answers to display
     * @param correctAnswer The index of the correct answer
     */
    @Validated
    data class QuestionData(
        @NotEmpty
        val title: String,
        @Size(min=1, message = "Must contain at least one answer")
        val answers: List<String>,
        @PositiveOrZero(message = "Answer index must be 0 or higher")
        val correctAnswer: Int
    )

    fun getAllCaptcha(): List<QuestionData> = questions
}