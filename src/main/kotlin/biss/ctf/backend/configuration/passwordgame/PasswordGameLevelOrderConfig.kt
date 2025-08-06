package biss.ctf.backend.configuration.passwordgame

import biss.ctf.backend.services.passwordlevels.*
import biss.ctf.backend.services.pythonExecutor.PythonExecutorService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PasswordGameLevelOrderConfig {

    @Bean
    fun passwordGameLevels(
        pythonExecutorService: PythonExecutorService,
        passwordGameLevelValueConfig: PasswordGameLevelValueConfig,
        passwordGameLevelDescriptionConfig: PasswordGameLevelDescriptionsConfig
    ): List<PasswordGameLevel> = listOf(
        LengthPasswordLevel(passwordGameLevelValueConfig.minimumPasswordLength),
        CapitalCharacterPasswordLevel(),
        SpecialCharacterPasswordLevel(),
        BissSpecificWordsPasswordLevel(
            passwordGameLevelDescriptionConfig.bissSpecificWords,
            passwordGameLevelValueConfig.bissSpecificWordsList
        ),
        HeartachePasswordLevel(),
        CoolestStateInAfricaPasswordLevel(),
        StrengthPasswordLevel(),
        IncludeCourseNumberPasswordLevel(passwordGameLevelValueConfig.bissNumber),
        IncludeSponsorPasswordLevel(passwordGameLevelValueConfig.bissSponsor),
        MonthPasswordLevel(),
        IncludeWordPalindromePasswordLevel(),
        PeriodicTablePasswordLevel(passwordGameLevelValueConfig.sumOfAtomicElements),
        WhoStartedTheFirePasswordLevel(),
        DigitsSumDivisibleByBissNumberPasswordLevel(passwordGameLevelValueConfig.bissNumber),
        EvenAmountOfEveryCharPasswordLevel(),
        LeapYearPasswordLevel(),
        RomanNumeralPasswordLevel(passwordGameLevelValueConfig.romanNumeralDevisor),
        NotIncludingPhrasePasswordLevel(passwordGameLevelValueConfig.phraseNotToInclude),
        WebsiteRatingPasswordLevel(),
        StaffHeightPasswordLevel(passwordGameLevelValueConfig.staffTotalHeightInCm),
        HexMirrorPasswordLevel(),
        PalindromePasswordLevel(),
        CompilationPasswordLevel(pythonExecutorService),
        PasswordOutputLevel(pythonExecutorService),
    )
}
