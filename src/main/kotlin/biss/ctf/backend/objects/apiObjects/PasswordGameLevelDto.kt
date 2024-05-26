package biss.ctf.backend.objects.apiObjects

import biss.ctf.backend.services.passwordlevels.PasswordGameLevel

data class PasswordGameLevelDto(val description: String, val hint: String) {
    companion object {
        fun fromPasswordGameLevel(level: PasswordGameLevel) =
            PasswordGameLevelDto(level.getLevelDescription(), level.getLevelHint())
    }
}