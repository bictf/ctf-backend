package biss.ctf.backend.services

import biss.ctf.backend.entities.UserDataEntity
import biss.ctf.backend.exceptions.UnauthorizedException
import biss.ctf.backend.objects.apiObjects.Megama
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
    fun findOrSaveUser(uuid: String, megama: Megama): UserDataEntity =
        findUserByUuid(uuid) ?: saveUser(uuid, megama)

    /**
     * Saves a new user to the user data storage.
     * @param uuid The UUID of the user to create.
     * @param megama The megama of the user to create.
     */
    private fun saveUser(uuid: String, megama: Megama): UserDataEntity = userDataRepository.save(
        UserDataEntity(
            uuid,
            megama,
            PasswordUtils.generateNewPassword(),
            false
        )
    )


    /**
     * Finds a user by their UUID.
     * @param uuid The UUID of the user to search for.
     * @return The user if found, null if not.
     */
    fun findUserByUuid(uuid: String): UserDataEntity? = userDataRepository.findById(uuid).orElse(null)

    /**
     * Sets the user with the given UUID as logged in.
     *
     * @param uuid The UUID of the user to set as logged in.
     */
    fun setUserLoggedIn(uuid: String) {
        val user = findUserByUuid(uuid)
            ?: throw NoSuchElementException("Attempted to set user with uuid '$uuid' in as logged in, but the user doesn't exist")
        user.hasLoggedIn = true
        userDataRepository.save(user)
    }

    /**
     * Sets the user with the given UUID as logged out and generates a new password.
     *
     * @param uuid The UUID of the user to set as logged out.
     */
    fun expireUserPassword(uuid: String) {
        val user = findUserByUuid(uuid)
            ?: throw NoSuchElementException("Attempted to expire password of user with uuid '$uuid', but the user doesn't exist")
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
    fun isUserLoggedIn(uuid: String): Boolean = findUserByUuid(uuid)?.hasLoggedIn ?: false


    /**
     * Asserts that a user is logged in.
     * @throws UnauthorizedException if the user is not logged in.
     */
    fun assertIsLoggedIn(uuid: String) {
        if (!isUserLoggedIn(uuid)) throw UnauthorizedException(uuid) else return
    }

    fun findUserMegama(uuid: String): Megama {
        return findUserByUuid(uuid)?.megama
            ?: throw NoSuchElementException("Attempted to find megama of user with uuid '$uuid', but the user doesn't exist")
    }
}