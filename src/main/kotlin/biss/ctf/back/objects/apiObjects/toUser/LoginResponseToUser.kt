package biss.ctf.back.objects.apiObjects.toUser

import java.util.*

data class LoginResponseToUser(
    val success: Boolean,
    val passwordDiff: ArrayList<Int>,
    val cookie: String
)