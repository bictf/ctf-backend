package biss.ctf.backend.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "signal-chart")
class SignalChartConfiguration {
    var passwordParts: List<String> = emptyList()
}