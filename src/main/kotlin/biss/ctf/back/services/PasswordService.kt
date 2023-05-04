package biss.ctf.back.services

import biss.ctf.back.objects.PasswordCharState
import org.springframework.aot.hint.TypeReference.listOf
import org.springframework.stereotype.Service
import java.util.*

@Service
class PasswordService() {
    fun generateNewPassword(): String {
        return "newPassword"
    }

    fun checkPasswordsDiff(password: String, newPassword: String): ArrayList<Int> {
        if(password.length != newPassword.length){
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