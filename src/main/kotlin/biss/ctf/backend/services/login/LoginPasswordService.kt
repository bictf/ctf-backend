package biss.ctf.backend.services.login

import biss.ctf.backend.objects.routing.CTFStage

//TODO(98) - doc
abstract class LoginPasswordService {
    abstract val ctfStage: CTFStage

    abstract fun handlePasswordAttempt(passwordAttempt: String, actualPassword: String): Pair<Any, Boolean>

    open fun validatePassword(inputtedPassword: String, actualPassword: String): Boolean =
        inputtedPassword == actualPassword
}