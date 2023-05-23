package biss.ctf.back.services

import biss.ctf.back.repositories.UserDataRepository
import org.springframework.stereotype.Service

@Service
class UserDataService(
        val userDataRepository: UserDataRepository
) {
    fun getUserByUUID(uuid: String) = userDataRepository.get(uuid)

}