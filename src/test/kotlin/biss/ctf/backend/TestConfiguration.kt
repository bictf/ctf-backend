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

        val runPython = if ("windows" in osName) ::runWindowsPythonWithUTF8 else ::runNixPythonWithUTF8

        every { mock.executeCode(capture(slot)) } answers {
            val (_, msg) = runPython(codeFile, slot.captured)
            msg
        }
        every { mock.doesCodeCompile(capture(slot)) } answers {
            val (wasSuccessful, msg) = runPython(codeFile, slot.captured)
            CompilationResponse(wasSuccessful, msg)
        }

        return mock
    }

    private fun runWindowsPythonWithUTF8(codeFile: Path, code: String): Pair<Boolean, String> {
        return executePython(codeFile, code, listOf("python"))
    }

    private fun runNixPythonWithUTF8(codeFile: Path, code: String): Pair<Boolean, String> {
        return executePython(codeFile, code, listOf("python3"))
    }

    private fun executePython(codeFile: Path, code: String, command: List<String>): Pair<Boolean, String> {
        codeFile.writeText(code, Charsets.UTF_8)

        val processBuilder = ProcessBuilder(command + codeFile.absolutePathString())
            .redirectErrorStream(true)

        // 🔹 Set environment variable PYTHONUTF8=1 properly
        processBuilder.environment()["PYTHONUTF8"] = "1"

        val process = processBuilder.start()
        val output = process.inputStream.bufferedReader(Charsets.UTF_8).readText()
        val exitCode = process.waitFor()

        return Pair(exitCode == 0, output)
    }

}
