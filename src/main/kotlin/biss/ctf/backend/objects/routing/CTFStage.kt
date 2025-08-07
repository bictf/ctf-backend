package biss.ctf.backend.objects.routing

enum class CTFStage(val isLoginStage: Boolean = false) {
    LOGIN_WORDLE(isLoginStage = true),
    LOGIN_SIGNAL_CHART(isLoginStage = true),
    LOGIN_DOCKER_IMAGES(isLoginStage = true),
    GOOLOOG,
    PASSWORD_GAME,
    END_OF_CTF,
}