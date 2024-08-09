package biss.ctf.backend

import biss.ctf.backend.entities.CompilationResponse
import biss.ctf.backend.services.pythonExecutor.PythonExecutorService
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.nio.file.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.writeText

@Profile("password-test")
@Configuration
class TestConfiguration {
    @Bean
    fun pythonExecutor(): PythonExecutorService {
        val slot = slot<String>()
        val mock = mockk<PythonExecutorService>()
        val codeFile = kotlin.io.path.createTempFile()
        val osName = System.getProperty("os.name").lowercase()
        if ("windows" in osName) {
            every { mock.executeCode(capture(slot)) } answers {
                val (_, msg) = runWindowsPython(codeFile, slot.captured)
                msg
            }
            every { mock.doesCodeCompile(capture(slot)) } answers {
                val (wasSuccessful, msg) = runWindowsPython(codeFile, slot.captured)
                CompilationResponse(wasSuccessful, msg)
            }
        } else {
            every { mock.executeCode(capture(slot)) } answers {
                val (_, msg) = runNixPython(codeFile, slot.captured)
                msg
            }
            every { mock.doesCodeCompile(capture(slot)) } answers {
                val (wasSuccessful, msg) = runNixPython(codeFile, slot.captured)
                CompilationResponse(wasSuccessful, msg)
            }
        }
        return mock
    }

    private fun runNixPython(codeFile: Path, code: String): Pair<Boolean, String> {
        return codeFile.writeText(code).run {
            val proc = nixProcessBuilder(codeFile).start()
            proc.waitFor()
            if (proc.exitValue() == 0) {
                Pair(true, proc.inputStream.reader().readText())
            } else {
                Pair(
                    false, proc.errorStream.reader()
                        .readText()
                )
            }
        }
    }

    private fun runWindowsPython(codeFile: Path, code: String): Pair<Boolean, String> {
        return codeFile.writeText(code).run {
            val proc = windowsProcessBuilder(codeFile).start()
            proc.waitFor()
            if (proc.exitValue() == 0) {
                Pair(true, proc.inputStream.reader().readText())
            } else {
                Pair(
                    false, proc.errorStream.reader()
                        .readText()
                )
            }
        }
    }

    private fun windowsProcessBuilder(codeFile: Path): ProcessBuilder =
        ProcessBuilder().command("python", codeFile.absolutePathString())

    private fun nixProcessBuilder(codeFile: Path): ProcessBuilder =
        ProcessBuilder().command("python3", codeFile.absolutePathString())
}
