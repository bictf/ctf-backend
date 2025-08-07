package biss.ctf.backend.services.login

import biss.ctf.backend.objects.apiObjects.Megama
import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.services.LoginStageResolver
import org.springframework.stereotype.Component

@Component
class LoginPasswordServiceFactory(
    private val loginPasswordServices: List<LoginPasswordService>,
    private val loginStageResolver: LoginStageResolver
) {
    private val loginPasswordServicesMap: Map<CTFStage, LoginPasswordService> =
        loginPasswordServices.associateBy { it.ctfStage }

    fun getLoginPasswordService(megama: Megama): LoginPasswordService =
        this.loginPasswordServicesMap[loginStageResolver.getLoginStage(megama)]!!
}