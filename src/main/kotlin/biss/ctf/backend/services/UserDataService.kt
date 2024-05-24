package biss.ctf.backend.services

import biss.ctf.backend.entities.UserDataEntity
import biss.ctf.backend.repositories.UserDataRepository
import biss.ctf.backend.utils.PasswordUtils
import org.springframework.stereotype.Service

@Service
class UserDataService(
    private val userDataRepository: UserDataRepository
) {

    /**
     * Finds a user by their UUID or creates a new user if none exists.
     *
     * @param uuid The UUID of the user to find or create.
     * @return The found or newly created UserDataEntity.
     */
    fun findOrSaveUserByUuid(uuid: String): UserDataEntity {
        if (userDataRepository.existsById(uuid)) {
            return userDataRepository.findById(uuid).get()
        }
        return userDataRepository.save(UserDataEntity(uuid, PasswordUtils.generateNewPassword(), false))
    }

    /**
     * Sets the user with the given UUID as logged in.
     *
     * @param uuid The UUID of the user to set as logged in.
     */
    fun setUserLoggedIn(uuid: String) {
        val user = findOrSaveUserByUuid(uuid)
        user.hasLoggedIn = true
        userDataRepository.save(user)
    }

    /**
     * Sets the user with the given UUID as logged out and generates a new password.
     *
     * @param uuid The UUID of the user to set as logged out.
     */
    fun setUserLoggedOut(uuid: String) {
        val user = findOrSaveUserByUuid(uuid)
        user.password = PasswordUtils.generateNewPassword()
        user.hasLoggedIn = false
        userDataRepository.save(user)
    }

    /**
     * Checks if the user with the given UUID is logged in.
     *
     * @param uuid The UUID of the user to check.
     * @return `true` if the user is logged in, `false` otherwise.
     */
    fun isUserLoggedIn(uuid: String): Boolean {
        val user = findOrSaveUserByUuid(uuid)
        return user.hasLoggedIn
    }

}