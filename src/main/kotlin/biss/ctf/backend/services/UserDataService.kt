package biss.ctf.backend.services

import biss.ctf.backend.entities.UserDataEntity
import biss.ctf.backend.events.ExpirePasswordEvent
import biss.ctf.backend.exceptions.UnauthorizedException
import biss.ctf.backend.objects.apiObjects.Megama
import biss.ctf.backend.repositories.UserDataRepository
import biss.ctf.backend.services.login.LoginPasswordServiceFactory
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class UserDataService(
    private val userDataRepository: UserDataRepository,
    private val loginPasswordServiceFactory: LoginPasswordServiceFactory
) {

    /**
     * Finds a user by their UUID or creates a new user if none exists.
     *
     * @param uuid The UUID of the user to find or create.
     * @return The found or newly created UserDataEntity.
     */
    fun findOrSaveUser(uuid: String, megama: Megama): UserDataEntity {
        val user = findUserByUuid(uuid) ?: saveUser(uuid, megama)

        if (user.megama != megama) throw IllegalArgumentException("User '$uuid' megama is different from user '$user' megama")

        return user
    }

    /**
     * Finds a user by their UUID.
     * @param uuid The UUID of the user to search for.
     * @return The user if found, null if not.
     */
    fun findUserByUuid(uuid: String): UserDataEntity? = userDataRepository.findById(uuid).orElse(null)

    /**
     * Saves a new user to the user data storage.
     * @param uuid The UUID of the user to create.
     * @param megama The megama of the user to create.
     */
    private fun saveUser(uuid: String, megama: Megama): UserDataEntity = userDataRepository.save(

        UserDataEntity(
            uuid,
            megama,
            this.loginPasswordServiceFactory.getLoginPasswordService(megama).generatePassword(uuid),
            false
        )
    )

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


    @EventListener
    fun expirePasswordOnEvent(event: ExpirePasswordEvent) {
        if (!isUserLoggedIn(event.uuid)) expireUserPassword(event.uuid)
    }

    /**
     * Sets the user with the given UUID as logged out and generates a new password.
     *
     * @param uuid The UUID of the user to set as logged out.
     */
    fun expireUserPassword(uuid: String) {
        val user = findUserByUuid(uuid)
            ?: throw NoSuchElementException("Attempted to expire password of user with uuid '$uuid', but the user doesn't exist")

        val loginPasswordService = loginPasswordServiceFactory.getLoginPasswordService(user.megama)

        user.password = loginPasswordService.generatePassword(uuid)
        user.hasLoggedIn = false
        userDataRepository.save(user)
    }
}