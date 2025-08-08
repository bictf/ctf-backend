package biss.ctf.backend.configuration

import biss.ctf.backend.objects.apiObjects.Megama
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "login")
class LoginConfiguration {
    var allowedUsers: Map<String, Megama> = emptyMap()
    var canRequestAdmin: List<Megama> = emptyList()
}