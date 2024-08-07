package biss.ctf.backend.services.passwordlevels

import biss.ctf.backend.services.pythonExecutor.PythonExecutorService
import org.springframework.stereotype.Component

@Component
class CompilationPasswordLevel(val pythonExecutorService: PythonExecutorService): PasswordGameLevel {
    override fun getLevelDescription(): String {

    }

    override fun getLevelHint(): String {
        return "The programming language is a fan of Maccabi"
    }

    override fun doesAnswerLevel(password: String): Boolean {
        TODO("Not yet implemented")
    }
}
