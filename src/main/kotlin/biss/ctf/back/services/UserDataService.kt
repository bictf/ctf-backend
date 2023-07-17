package biss.ctf.back.services

import biss.ctf.back.repositories.UserDataRepository
import org.springframework.stereotype.Service

@Service
class UserDataService(
        val userDataRepository: UserDataRepository
) {
    fun getUserByUUID(uuid: String) = userDataRepository.get(uuid)

    fun userLoggedIn(uuid: String) = userDataRepository.userLoggedIn(uuid)

    fun userLoggedOut(uuid: String) = userDataRepository.userLoggedOut(uuid)

    fun doesUserLoggedIn(uuid: String) = userDataRepository.doesUserLoggedIn(uuid)

    fun userCompletedLevel(uuid: String, levelId: String, answer: Boolean) = userDataRepository.userCompletedLevel(uuid, levelId, answer)

    fun doesUserCompletedLevels(uuid: String) = userDataRepository.doesUserCompletedLevels(uuid)
}