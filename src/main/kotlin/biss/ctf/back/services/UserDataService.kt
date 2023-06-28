package biss.ctf.back.services

import biss.ctf.back.repositories.UserDataRepository
import org.springframework.stereotype.Service

@Service
class UserDataService(
        val userDataRepository: UserDataRepository
) {
    fun getUserByUUID(uuid: String) = userDataRepository.get(uuid)

    fun userLoggedIn(uuid: String) = userDataRepository.userLoggedIn(uuid)

    fun doesUserLoggedIn(uuid: String) = userDataRepository.doesUserLoggedIn(uuid)
}