package biss.ctf.back.repositories

import biss.ctf.back.entities.UserDataEntity
import biss.ctf.back.services.PasswordService
import mu.KotlinLogging
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserDataRepository (
    val passwordService: PasswordService
){

    private val users = HashMap<String, UserDataEntity>()
    private val logger = KotlinLogging.logger {}

    fun set(userDataEntity: UserDataEntity) {
        users[userDataEntity.UUID] = userDataEntity
    }

    fun get(uuid: String): UserDataEntity {
        users.putIfAbsent(uuid, UserDataEntity(uuid, passwordService.generateNewPassword(), false))
        val user = users[uuid]!!
        logger.info("Logging in for user \"${user.UUID}\" and password \"${user.password}\"")
        return users[uuid]!!
    }

    fun userLoggedIn(uuid: String) {
        val user = get(uuid)

        user.hasLoggedIn = true

        set(user)
    }

    fun doesUserLoggedIn(uuid: String): Boolean {
        val user = get(uuid)

        return user.hasLoggedIn
    }
}