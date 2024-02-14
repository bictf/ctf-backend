package biss.ctf.backend.services.captchas

import biss.ctf.backend.entities.ChoiceQuestionData
import biss.ctf.backend.entities.OpenQuestionData
import org.springframework.stereotype.Service

@Service
class CaptchaQuestionService(
    val choiceQuestions: List<ChoiceQuestionData>,
    val openQuestions: List<OpenQuestionData>
) {
    fun getAllChoiceCaptcha(): List<ChoiceQuestionData> = choiceQuestions
    fun getAllOpenCaptcha(): List<OpenQuestionData> = openQuestions
}