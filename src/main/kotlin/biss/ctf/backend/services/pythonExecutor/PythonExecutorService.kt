package biss.ctf.backend.services.pythonExecutor

import biss.ctf.backend.entities.CompilationResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class PythonExecutorService(private val webClient: WebClient) {
    fun doesCodeCompile(
        code: String,
        @Value("\$python-executor.endpoints.compile") compileEndpoint: String
    ): CompilationResponse {
        return webClient.get().uri { uriBuilder -> uriBuilder.path(compileEndpoint).queryParam("code", code).build() }
            .retrieve()
            .bodyToMono(CompilationResponse::class.java)
            .block()
    }

    fun executeCode(
        code: String,
        @Value("\$python-executor.endpoints.execute") executeEndpoint: String
    ): String {
        return webClient.get().uri { uriBuilder -> uriBuilder.path(executeEndpoint).queryParam("code", code).build() }
            .retrieve()
            .bodyToMono(String::class.java)
            .block()
    }
}