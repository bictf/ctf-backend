package biss.ctf.back.repositories

import biss.ctf.back.entities.UserDataEntity
import biss.ctf.back.services.PasswordService
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class UserDataRepository (
    val passwordService: PasswordService
){

    private val users = HashMap<String, UserDataEntity>()

    fun set(userDataEntity: UserDataEntity) {
        users[userDataEntity.UUID] = userDataEntity
    }

    fun get(uuid: String): UserDataEntity {
        users.putIfAbsent(uuid, UserDataEntity(uuid, passwordService.generateNewPassword(), false))
        return users[uuid]!!
    }

    fun userLoggedIn(uuid: String) {
        val user = get(uuid)

        user.hasLoggedIn = true

        set(user)
    }

    fun userLoggedOut(uuid: String) {
        val user = get(uuid)

        user.password = passwordService.generateNewPassword()
        user.hasLoggedIn = false

        set(user)
    }

    fun doesUserLoggedIn(uuid: String): Boolean {
        val user = get(uuid)

        return user.hasLoggedIn
    }
}