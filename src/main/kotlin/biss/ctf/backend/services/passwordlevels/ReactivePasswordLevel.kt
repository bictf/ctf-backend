package biss.ctf.backend.services.passwordlevels

import biss.ctf.backend.objects.apiObjects.PasswordGameLevelDto

abstract class ReactivePasswordLevel : PasswordGameLevel {
    companion object {
        const val DEFAULT_RESPONSE = "Failed to execute level - contact segel"
    }

    override fun getLevelDescription(): String {
        throw UnsupportedOperationException("Reactive level - cannot get levelDescription")
    }

    override fun doesAnswerLevel(password: String): Boolean {
        return this.execute(password)?.isCorrect ?: false
    }

    fun invoke(password: String): PasswordGameLevelDto {
        return this.execute(password) ?: PasswordGameLevelDto(DEFAULT_RESPONSE, false)
    }

    abstract fun execute(password: String): PasswordGameLevelDto?
}