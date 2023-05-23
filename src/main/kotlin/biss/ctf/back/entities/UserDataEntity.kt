package biss.ctf.back.entities

data class UserDataEntity(
    val UUID: String,
    val password: String,
    val expirationDate: Long,
)