package biss.ctf.backend.services.routing

import biss.ctf.backend.configuration.CTFRoute
import biss.ctf.backend.configuration.RoutingConfiguration
import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.objects.apiObjects.Megama
import mu.KotlinLogging
import org.springframework.stereotype.Service
import javax.naming.ConfigurationException

//TODO(98) - doc
@Service
class StageRouter(private val routingConfiguration: RoutingConfiguration) {
    companion object {
        val logger = KotlinLogging.logger(StageRouter::class.java.name)
    }

    fun getNextStage(megama: Megama, currentStage: CTFStage): CTFStage {
        if (megama !in routingConfiguration.routingPaths.keys) {
            logger.error("Megama ${megama.name} not mapped to a routing path!")
            throw ConfigurationException("Megama ${megama.name} not mapped to a routing path!")
        }

        val megamaRoute: CTFRoute = routingConfiguration.routingPaths[megama]!!
        if (currentStage !in megamaRoute) {
            logger.error("Stage $currentStage not in ${megama.name}'s routing path!")
            throw ConfigurationException("Megama ${megama.name} not mapped to a routing path!")
        }

        return megamaRoute.getOrElse(megamaRoute.indexOf(currentStage) + 1) {
            throw ConfigurationException("Couldn't get the CTF stage after ${currentStage}!")
        }
    }

    fun getLoginStage(megama: Megama): CTFStage {
        if (megama !in routingConfiguration.routingPaths.keys) {
            logger.error("Megama ${megama.name} not mapped to a routing path!")
            throw ConfigurationException("Megama ${megama.name} not mapped to a routing path!")
        }

        return this.routingConfiguration.routingPaths[megama]!!.first()
    }
}