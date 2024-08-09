package biss.ctf.backend.services.pythonExecutor

import biss.ctf.backend.entities.CompilationResponse
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

/**
 * Service class responsible for interacting with the Python code executor service.
 * Provides methods to check if Python code compiles and to execute Python code.
 *
 * @property webClient WebClient instance for making HTTP requests to the Python executor service.
 */
@Service
@Profile("!password-test")
class PythonExecutorService(
    private val webClient: WebClient
) {
    companion object {
        const val COMPILATION_ENDPOINT = "compile"
        const val EXECUTION_ENDPOINT = "execute"
    }

    /**
     * Sends a request to the Python code compilation endpoint to check if the provided Python code compiles.
     *
     * @param code The Python code to check for compilation.
     * @return A CompilationResponse object indicating whether the code compiled successfully.
     *         If the service cannot be reached, returns a default CompilationResponse with a failure message.
     */
    fun doesCodeCompile(
        code: String,
    ): CompilationResponse? {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path(COMPILATION_ENDPOINT)
                    .queryParam("code", code)
                    .build()
            }
            .retrieve()
            .bodyToMono(CompilationResponse::class.java)
            .block()
    }

    /**
     * Sends a request to the Python code execution endpoint to execute the provided Python code.
     *
     * @param code The Python code to execute.
     * @return A String containing the output of the executed code.
     *         If the service cannot be reached, returns a default message.
     */
    fun executeCode(
        code: String
    ): String? {
        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder.path(EXECUTION_ENDPOINT)
                    .queryParam("code", code)
                    .build()
            }
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
            .takeIf { it?.isNotBlank() ?: false }
    }
}
