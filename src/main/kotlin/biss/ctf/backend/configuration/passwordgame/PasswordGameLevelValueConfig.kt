package biss.ctf.backend.configuration.passwordgame

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:password-game.properties")
@ConfigurationProperties(prefix = "password-game.value")
class PasswordGameLevelValueConfig {

    lateinit var minimumPasswordLength: String
    lateinit var bissSpecificWordsList: List<String>
    lateinit var bissNumber: String
    lateinit var bissSponsor: String
    lateinit var sumOfAtomicElements: String
    lateinit var romanNumeralDevisor: String
    lateinit var phraseNotToInclude: String
    lateinit var staffTotalHeightInCm: String

    @Bean
    fun minimumPasswordLength(): Int = minimumPasswordLength.toInt()
    @Bean
    fun bissSpecificWordList(): List<String> = bissSpecificWordsList
    @Bean
    fun bissNumber(): Int = bissNumber.toInt()
    @Bean
    fun bissSponsor(): String = bissSponsor
    @Bean
    fun sumOfAtomicElements(): Int = sumOfAtomicElements.toInt()
    @Bean
    fun romanNumeralDevisor(): Int = romanNumeralDevisor.toInt()
    @Bean
    fun phraseNotToInclude(): String = phraseNotToInclude
    @Bean
    fun staffTotalHeightInCm(): Int = staffTotalHeightInCm.toInt()
}
