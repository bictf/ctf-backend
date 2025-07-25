package biss.ctf.backend

import biss.ctf.backend.services.passwordlevels.PasswordGameLevel
import mu.KotlinLogging
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles(profiles = ["password-test"])
class PasswordGameTest {
    companion object {
        private const val MASTER_PASSWORD =
            "c=3;print(\"6a=6=55cLiheartacheLi💪💪⭐⭐⭐⭐⭐orchidorchidc341Chad4153olga3513palindRomeCa338Cahufflepuff190622Sn!Sn⭐1906May18COMETCORALrtcClg3inm!elmoelmoyFm00ETALFm3p(ri)ntc\");a=3"
        private val logger = KotlinLogging.logger(PasswordGameTest::class.java.name)
    }

    @Autowired
    lateinit var levels: List<PasswordGameLevel>


    @Test
    fun `Given all password levels, A solution is available`() {
        levels.forEach {
            logger.info { "Testing '$MASTER_PASSWORD' for '${it::class.java.name}'" }
            assert(it.doesAnswerLevel(MASTER_PASSWORD))
        }
    }
}