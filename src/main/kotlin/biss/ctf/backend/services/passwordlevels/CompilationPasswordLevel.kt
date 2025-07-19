package biss.ctf.backend.services.passwordlevels

import biss.ctf.backend.objects.apiObjects.PasswordGameLevelDto
import biss.ctf.backend.services.pythonExecutor.PythonExecutorService

class CompilationPasswordLevel(val pythonExecutorService: PythonExecutorService) : ReactivePasswordLevel() {
    override fun execute(password: String): PasswordGameLevelDto? {
        return pythonExecutorService.doesCodeCompile(password)
            ?.let { PasswordGameLevelDto(if (it.isValid) "" else it.output, it.isValid) }
    }

    override fun getLevelHint(): String {
        return "The programming language is a fan of Maccabi"
    }
}
