package biss.ctf.backend.repositories

import biss.ctf.backend.entities.UserDataEntity
import biss.ctf.backend.utils.PasswordUtils
import org.springframework.stereotype.Repository

@Repository
class UserDataRepository {

    private val users = HashMap<String, UserDataEntity>()

    fun set(userDataEntity: UserDataEntity) {
        users[userDataEntity.UUID] = userDataEntity
    }

    fun get(uuid: String): UserDataEntity {
        users.putIfAbsent(uuid, UserDataEntity(uuid, PasswordUtils.generateNewPassword(), false))
        return users[uuid]!!
    }

    fun userLoggedIn(uuid: String) {
        val user = get(uuid)

        user.hasLoggedIn = true

        set(user)
    }

    fun userLoggedOut(uuid: String) {
        val user = get(uuid)

        user.password = PasswordUtils.generateNewPassword()
        user.hasLoggedIn = false

        set(user)
    }

    fun doesUserLoggedIn(uuid: String): Boolean {
        val user = get(uuid)

        return user.hasLoggedIn
    }

}