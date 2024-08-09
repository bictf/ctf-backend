package biss.ctf.backend.services.passwordlevels

import biss.ctf.backend.objects.apiObjects.PasswordGameLevelDto
import biss.ctf.backend.services.pythonExecutor.PythonExecutorService
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class PasswordOutputLevel(
    val pythonExecutorService: PythonExecutorService
) : ReactivePasswordLevel() {
    override fun execute(password: String): PasswordGameLevelDto? {
        return pythonExecutorService.executeCode(password)?.let {
            PasswordGameLevelDto(it, true)
        } ?: PasswordGameLevelDto(getLevelHint(), false)
    }

    override fun getLevelHint(): String {
        return "Check this folder!"
    }
}