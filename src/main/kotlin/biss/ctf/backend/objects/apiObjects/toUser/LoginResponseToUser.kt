package biss.ctf.backend.objects.apiObjects.toUser

import biss.ctf.backend.objects.routing.CTFStage

data class LoginResponseToUser(
    val success: Boolean,
    val cookie: String,
    val loginType: CTFStage,
    val passwordData: Any
)