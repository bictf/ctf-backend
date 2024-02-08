package biss.ctf.backend.services

import biss.ctf.backend.repositories.UserDataRepository
import org.springframework.stereotype.Service

@Service
class UserDataService(
    val userDataRepository: UserDataRepository
) {
    fun getUserByUUID(uuid: String) = userDataRepository.get(uuid)

    fun userLoggedIn(uuid: String) = userDataRepository.userLoggedIn(uuid)

    fun userLoggedOut(uuid: String) = userDataRepository.userLoggedOut(uuid)

    fun doesUserLoggedIn(uuid: String) = userDataRepository.doesUserLoggedIn(uuid)

}