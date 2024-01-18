package biss.ctf.back.services

import org.springframework.stereotype.Service
import java.util.*

const val KEY = "mistake"

@Service
class EncryptService {
    fun encrypt(input: String): String {
        val output = StringBuilder()

        for (i in input.indices) {
            val a: Int = input[i].toInt()
            val b: Int = KEY[i % KEY.length].toInt()
            output.append((a xor b).toChar())
        }

        return Base64.getEncoder().encodeToString(output.toString().toByteArray())
    }
}