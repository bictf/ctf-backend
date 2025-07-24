package biss.ctf.backend.configuration.passwordgame

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "password-game.values")
class PasswordGameLevelValueConfig {

    var minimumPasswordLength: Int = 0
    var bissNumber: Int = 0
    var sumOfAtomicElements: Int = 0
    var romanNumeralDevisor: Int = 0
    var staffTotalHeightInCm: Int = 0
    lateinit var bissSponsor: String
    lateinit var phraseNotToInclude: String
    lateinit var bissSpecificWordsList: List<String>
}
