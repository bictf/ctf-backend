package biss.ctf.backend.entities

data class UserDataEntity(
    val UUID: String,
    var password: String,
    var hasLoggedIn: Boolean,
)