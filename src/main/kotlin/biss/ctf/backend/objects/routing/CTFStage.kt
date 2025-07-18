package biss.ctf.backend.objects.routing

enum class CTFStage(val isLoginStage: Boolean = false) {
    LOGIN_WORDLE(isLoginStage = true),
    LOGIN_SIGNAL_GRAPH(isLoginStage = true),
    GOOLOOG,
    PASSWORD_GAME,
    END_OF_CTF,
}