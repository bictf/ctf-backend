package biss.ctf.backend.utils

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
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

            return Base64.encode(output.toString().toByteArray())
        }

        fun decrypt(input: String): String {
            val output = StringBuilder()
            val byteValues = Base64.decode(input.toByteArray())
            for (i in byteValues.indices) {
                val a: Int = byteValues[i].toInt()
                val b: Int = KEY[i % KEY.length].code
                output.append((a xor b).toChar())
            }

            return output.toString()
        }
    }
}