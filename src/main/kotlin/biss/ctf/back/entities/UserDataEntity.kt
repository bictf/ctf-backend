package biss.ctf.back.entities

data class UserDataEntity(
    val IP: String,
    val password: String,
    val expirationDate: Long,
)