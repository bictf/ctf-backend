package biss.ctf.backend.objects.apiObjects

import biss.ctf.backend.utils.EncryptUtils
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@Serializable
class SecretUserCookie(
    val uuid: String,
    val isAdmin: Boolean,
    val username: String,
    val secret: String
) {
    companion object {
        @Throws(SerializationException::class)
        fun fromEncryptedJson(encryptedCookie: String): SecretUserCookie {
            val decryptedCookieData = EncryptUtils.decrypt(encryptedCookie)
            return Json.decodeFromString<SecretUserCookie>(decryptedCookieData)
        }
    }
}