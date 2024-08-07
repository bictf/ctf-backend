package biss.ctf.backend.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class PasswordGameConfiguration {
    @Bean
    fun webClient(@Value("\${python-executor.route}") pythonExecutorRoute: String): WebClient {
        return WebClient.builder()
            .baseUrl(pythonExecutorRoute) // Default base URL if needed
            .build()
    }
}