package biss.ctf.backend.objects.apiObjects.toUser

data class LoginResponseToUser(
    val success: Boolean,
    val cookie: String,
    val passwordData: Any
)