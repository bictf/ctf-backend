package biss.ctf.backend.services.login

//TODO(98) - doc
abstract class LoginPasswordService {
    abstract fun handlePasswordAttempt(passwordAttempt: String, actualPassword: String): Pair<Any, Boolean>

    open fun validatePassword(inputtedPassword: String, actualPassword: String): Boolean =
        inputtedPassword == actualPassword
}