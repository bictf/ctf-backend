package biss.ctf.backend.exceptions

class UnauthorizedException(uuid: String) : Exception("Unauthorized operation was attempted by '$uuid'")