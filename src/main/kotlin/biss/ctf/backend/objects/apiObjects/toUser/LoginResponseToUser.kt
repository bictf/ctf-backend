package biss.ctf.backend.objects.apiObjects.toUser

data class LoginResponseToUser(
    val success: Boolean,
    val passwordDiff: ArrayList<Int>,
    val cookie: String,
    val time: Long
)