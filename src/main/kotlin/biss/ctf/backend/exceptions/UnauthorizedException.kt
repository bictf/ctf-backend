package biss.ctf.backend.exceptions

class UnauthorizedException(uuid: String, message: String? = null) :
    Exception(message ?: "Unauthorized operation was attempted by '$uuid'")