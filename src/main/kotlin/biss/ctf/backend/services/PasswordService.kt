package biss.ctf.back.services

import biss.ctf.back.objects.PasswordCharState
import org.springframework.stereotype.Service
import kotlin.random.Random

const val MINIMUM_PASSWORD_LENGTH = 24
const val MAXIMUM_PASSWORD_LENGTH = 28
val PASSWORD_CHAR_POOL = ('0'..'9') + ('a'..'z') + ('A'..'Z')

@Service
class PasswordService {
    fun generateNewPassword(): String {
        val passwordLength = Random.nextInt(MINIMUM_PASSWORD_LENGTH, MAXIMUM_PASSWORD_LENGTH)
        val charPool: MutableList<Char> = PASSWORD_CHAR_POOL.map { it } as MutableList<Char>

        return (1..passwordLength)
            .map {
                Random.nextInt(0, charPool.size).let {
                    val temp = charPool[it]
                    charPool.removeAt(it)
                    temp
                }
            }
            .joinToString("")
    }

    fun isPasswordTrue(passwordDiff: ArrayList<Int>): Boolean {
        return !passwordDiff.contains(PasswordCharState.CorrectCharWrongPlace.ordinal) &&
                !passwordDiff.contains(PasswordCharState.IncorrectChar.ordinal)
    }

    fun checkPasswordsDiff(password: String, newPassword: String): ArrayList<Int> {
        if (password.length != newPassword.length) {
            throw Exception("Password is in the wrong length!")
        }

        val passwordDiff = ArrayList<Int>()

        for (i in password.indices) {
            if (newPassword[i] == password[i]) {
                passwordDiff.add(PasswordCharState.CorrectCharCorrectPlace.ordinal)
            } else if (password.contains(newPassword[i])) {
                passwordDiff.add(PasswordCharState.CorrectCharWrongPlace.ordinal)
            } else {
                passwordDiff.add(PasswordCharState.IncorrectChar.ordinal)
            }
        }

        return passwordDiff
    }
}