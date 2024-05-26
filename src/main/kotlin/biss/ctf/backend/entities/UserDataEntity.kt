package biss.ctf.backend.entities

import jakarta.persistence.Entity
import jakarta.persistence.Id


@Entity
data class UserDataEntity(
    @Id
    val UUID: String,
    var password: String,
    var hasLoggedIn: Boolean,
)