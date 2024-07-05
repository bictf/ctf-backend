package biss.ctf.backend.services

import biss.ctf.backend.services.passwordlevels.PasswordGameLevel
import org.springframework.stereotype.Service

@Service
class PasswordGameService(
    private val passwordLevels: List<PasswordGameLevel>
) {
    fun getAllLevels(from: Int = 0, upTo: Int? = null) = passwordLevels.subList(from, upTo ?: passwordLevels.size)

    fun getLevelCount() = passwordLevels.size

    // TODO implement game ending logic. **IMPORTANT** the above wil return null also if the answer is correct for all the levels.
}