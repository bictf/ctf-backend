package biss.ctf.backend.objects.apiObjects

import biss.ctf.backend.utils.EncryptUtils
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class UserCookieData(
    val uuid: String,
    val isAdmin: Boolean,
    val username: String,
) {
    companion object {
        fun fromEncryptedJson(encryptedCookie: String) =
            Json.decodeFromString<UserCookieData>(EncryptUtils.decrypt(encryptedCookie))
    }

    fun toEncryptedJson() = EncryptUtils.encrypt(Json.encodeToString(this))
}