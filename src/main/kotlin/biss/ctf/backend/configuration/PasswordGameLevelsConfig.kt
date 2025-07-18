package biss.ctf.backend.configuration

import biss.ctf.backend.services.passwordlevels.CapitalCharacterPasswordLevel
import biss.ctf.backend.services.passwordlevels.CometAndCoralPasswordLevel
import biss.ctf.backend.services.passwordlevels.CompilationPasswordLevel
import biss.ctf.backend.services.passwordlevels.CoolestStateInAfricaPasswordLevel
import biss.ctf.backend.services.passwordlevels.DigitsSumDivisibleBy18PasswordLevel
import biss.ctf.backend.services.passwordlevels.EvenAmountOfEveryCharPasswordLevel
import biss.ctf.backend.services.passwordlevels.HeartachePasswordLevel
import biss.ctf.backend.services.passwordlevels.HexMirrorPasswordLevel
import biss.ctf.backend.services.passwordlevels.IncludeCourseNumberPasswordLevel
import biss.ctf.backend.services.passwordlevels.IncludeSponsorPasswordLevel
import biss.ctf.backend.services.passwordlevels.IncludeWordPalindromePasswordLevel
import biss.ctf.backend.services.passwordlevels.LeapYearPasswordLevel
import biss.ctf.backend.services.passwordlevels.LengthPasswordLevel
import biss.ctf.backend.services.passwordlevels.MonthPasswordLevel
import biss.ctf.backend.services.passwordlevels.NotIncludingPhrasePasswordLevel
import biss.ctf.backend.services.passwordlevels.PaiShoSpecialFlowerPasswordLevel
import biss.ctf.backend.services.passwordlevels.PalindromePasswordLevel
import biss.ctf.backend.services.passwordlevels.PasswordGameLevel
import biss.ctf.backend.services.passwordlevels.PasswordOutputLevel
import biss.ctf.backend.services.passwordlevels.PeriodicTablePasswordLevel
import biss.ctf.backend.services.passwordlevels.RomanNumeralPasswordLevel
import biss.ctf.backend.services.passwordlevels.SpecialCharacterPasswordLevel
import biss.ctf.backend.services.passwordlevels.StaffHeightPasswordLevel
import biss.ctf.backend.services.passwordlevels.StrengthPasswordLevel
import biss.ctf.backend.services.passwordlevels.WebsiteRatingPasswordLevel
import biss.ctf.backend.services.passwordlevels.WhoStartedTheFirePasswordLevel
import biss.ctf.backend.services.pythonExecutor.PythonExecutorService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PasswordGameLevelsConfig {

    @Bean
    fun passwordGameLevels(
        pythonExecutorService: PythonExecutorService,
    ): List<PasswordGameLevel> = listOf(
        LengthPasswordLevel(),
        CapitalCharacterPasswordLevel(),
        SpecialCharacterPasswordLevel(),
        PaiShoSpecialFlowerPasswordLevel(),
        CometAndCoralPasswordLevel(),
        HeartachePasswordLevel(),
        CoolestStateInAfricaPasswordLevel(),
        StrengthPasswordLevel(),
        IncludeCourseNumberPasswordLevel(),
        IncludeSponsorPasswordLevel(),
        MonthPasswordLevel(),
        IncludeWordPalindromePasswordLevel(),
        PeriodicTablePasswordLevel(),
        WhoStartedTheFirePasswordLevel(),
        DigitsSumDivisibleBy18PasswordLevel(),
        EvenAmountOfEveryCharPasswordLevel(),
        LeapYearPasswordLevel(),
        RomanNumeralPasswordLevel(),
        NotIncludingPhrasePasswordLevel(),
        WebsiteRatingPasswordLevel(),
        StaffHeightPasswordLevel(),
        HexMirrorPasswordLevel(),
        PalindromePasswordLevel(),
        CompilationPasswordLevel(pythonExecutorService),
        PasswordOutputLevel(pythonExecutorService),
    )
}