package biss.ctf.backend.services

import biss.ctf.backend.services.passwordlevels.PasswordGameLevel
import org.springframework.stereotype.Service

@Service
class PasswordGameService(
    private val passwordLevels: List<PasswordGameLevel>
) {
    private val userLevelMap = HashMap<String, List<PasswordGameLevel>>()

    fun getUserLevels(uuid: String): List<PasswordGameLevel> {
        return userLevelMap.computeIfAbsent(uuid) { _ -> passwordLevels.shuffled() }
    }

    fun getNextUserLevel(uuid: String, currentPassword: String): PasswordGameLevel? {
        return runCatching {  getUserLevels(uuid).first { !it.doesAnswerLevel(currentPassword) } }.getOrNull()
    }

    fun getAllLevels() = passwordLevels

    fun getNextLevel(currentPassword: String) = getAllLevels().first { !it.doesAnswerLevel(currentPassword) }

    // TODO implement game ending logic. **IMPORTANT** the above wil return null also if the answer is correct for all the levels.
}