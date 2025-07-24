package biss.ctf.backend.configuration.passwordgame

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ConfigurationProperties(prefix = "password-game.descriptions")
class PasswordGameLevelDescriptionsConfig {

    lateinit var bissSpecificWords: String
}
