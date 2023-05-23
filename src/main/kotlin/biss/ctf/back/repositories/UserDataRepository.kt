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
        users.put(userDataEntity.UUID, userDataEntity)
    }

    fun get(uuid: String): UserDataEntity {
        users.putIfAbsent(uuid, UserDataEntity(uuid, passwordService.generateNewPassword(), 0L))
        val user = users[uuid]!!
        logger.info("Logging in for user \"${user.UUID}\" and password \"${user.password}\"")
        return users[uuid]!!
    }
}