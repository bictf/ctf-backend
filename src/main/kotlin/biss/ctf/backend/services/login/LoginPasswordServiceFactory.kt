package biss.ctf.backend.services.login

import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.objects.apiObjects.Megama
import biss.ctf.backend.services.routing.StageRouter
import org.springframework.stereotype.Component

@Component
class LoginPasswordServiceFactory(
    loginPasswordServices: List<LoginPasswordService>,
    private val stageRouter: StageRouter
) {
    private val loginPasswordServicesMap: Map<CTFStage, LoginPasswordService> =
        loginPasswordServices.associateBy { it.ctfStage }

    fun getLoginPasswordService(megama: Megama): LoginPasswordService =
        this.loginPasswordServicesMap[stageRouter.getLoginStage(megama)]!!
}