package biss.ctf.backend.configuration.passwordgame

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:password-game.properties")
@ConfigurationProperties(prefix = "password-game.level-description")
class PasswordGameLevelsDescriptionConfig {

    lateinit var bissSpecificWords: String

    @Bean
    fun bissSpecificWordsLevelDescription(): String = bissSpecificWords
}
