package biss.ctf.backend.services

import biss.ctf.backend.configuration.RoutingConfiguration
import biss.ctf.backend.objects.apiObjects.Megama
import biss.ctf.backend.objects.routing.CTFStage
import org.springframework.stereotype.Service
import javax.naming.ConfigurationException

@Service
class LoginStageResolver(private val routingConfiguration: RoutingConfiguration) {
    /**
     * Gets the corresponding login page for a megama.
     *
     * @param megama The megama to get the login page for
     * @return The login stage.
     */
    fun getLoginStage(megama: Megama): CTFStage {
        return routingConfiguration.routingPaths[megama]?.first()
            ?: throw ConfigurationException("Megama ${megama.name} not mapped to a routing path!")
    }
}