package biss.ctf.backend.services.passwordlevels

import biss.ctf.backend.objects.apiObjects.PasswordGameLevelDto
import biss.ctf.backend.services.pythonExecutor.PythonExecutorService

class PasswordOutputLevel(
    val pythonExecutorService: PythonExecutorService
) : ReactivePasswordLevel() {
    override fun execute(password: String): PasswordGameLevelDto? {
        return pythonExecutorService.executeCode(password)?.let {
            PasswordGameLevelDto(it, true)
        } ?: PasswordGameLevelDto(getLevelHint(), false)
    }

    override fun getLevelHint(): String {
        return "The servers are down so I'm running the code on my personal computer... That's secure enough right?"
    }
}