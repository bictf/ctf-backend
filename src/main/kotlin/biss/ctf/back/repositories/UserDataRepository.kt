package biss.ctf.back.repositories

import biss.ctf.back.entities.UserDataEntity
import org.springframework.stereotype.Repository
import java.util.*
import java.util.Collections.emptyMap

@Repository
class UserDataRepository {

    private val users = HashMap<String, UserDataEntity>()

    fun set(userDataEntity: UserDataEntity) {
        users.put(userDataEntity.IP, userDataEntity)
    }

    fun get(IP: String): UserDataEntity {
        users.put("11", UserDataEntity("11", "Cc123456", 0L))

        return users[IP] ?: throw IllegalArgumentException("User with IP [${IP}] doesn't exist")
    }
}