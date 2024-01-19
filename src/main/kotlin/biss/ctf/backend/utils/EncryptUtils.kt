package biss.ctf.backend.utils

import java.util.*

class EncryptUtils {
    companion object {
        const val KEY = "mistake"

        fun encrypt(input: String): String {
            val output = StringBuilder()

            for (i in input.indices) {
                val a: Int = input[i].code
                val b: Int = KEY[i % KEY.length].code
                output.append((a xor b).toChar())
            }

            return Base64.getEncoder().encodeToString(output.toString().toByteArray())
        }
    }
}