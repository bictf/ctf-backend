package biss.ctf.backend.services.routing

import biss.ctf.backend.configuration.CTFRoute
import biss.ctf.backend.configuration.RoutingConfiguration
import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.objects.apiObjects.Megama
import biss.ctf.backend.repositories.UserDataRepository
import biss.ctf.backend.repositories.UserStageRepository
import biss.ctf.backend.services.UserDataService
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import javax.naming.ConfigurationException

//TODO(98) - doc
@Service
class StageRouter(
    private val routingConfiguration: RoutingConfiguration,
    private val userStageRepository: UserStageRepository,
    private val userDataService: UserDataService
) {
    companion object {
        val logger = KotlinLogging.logger(StageRouter::class.java.name)
    }

    fun getLoginStage(megama: Megama): CTFStage {
        if (megama !in routingConfiguration.routingPaths.keys) {
            logger.error("Megama ${megama.name} not mapped to a routing path!")
            throw ConfigurationException("Megama ${megama.name} not mapped to a routing path!")
        }

        return this.routingConfiguration.routingPaths[megama]!!.first()
    }

    fun getUserNextStage(uuid: String): CTFStage {
        val userMegama = userDataService.findUserByUuid(uuid)
        if (this.userStageRepository.existsById(uuid)) {
            val currentUserStage = this.userStageRepository.findById(uuid).get()

            return currentUserStage.getNextStage(userDataService.findUserMegama(uuid))
        }

        logger.error("No current stage is saved for user with uuid $uuid!")
        //TODO(98) exception handle and return indicative message to the user
        throw EntityNotFoundException("No current stage is saved for user with uuid $uuid!")
    }

    private fun CTFStage.getNextStage(megama: Megama): CTFStage {
        if (megama !in routingConfiguration.routingPaths.keys) {
            logger.error("Megama ${megama.name} not mapped to a routing path!")
            throw ConfigurationException("Megama ${megama.name} not mapped to a routing path!")
        }

        val megamaRoute: CTFRoute = routingConfiguration.routingPaths[megama]!!
        if (this !in megamaRoute) {
            logger.error("Stage $this not in ${megama.name}'s routing path!")
            throw ConfigurationException("Megama ${megama.name} not mapped to a routing path!")
        }

        return megamaRoute.getOrElse(megamaRoute.indexOf(this) + 1) {
            throw ConfigurationException("Couldn't get the CTF stage after ${this}!")
        }
    }
}