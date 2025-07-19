package biss.ctf.backend.entities

import biss.ctf.backend.objects.apiObjects.Megama
import jakarta.persistence.Entity
import jakarta.persistence.Id


@Entity
data class UserDataEntity(
    @Id
    val UUID: String,
    val megama: Megama,
    var password: String,
    var hasLoggedIn: Boolean,
)