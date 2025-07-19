package biss.ctf.backend.services.routing

import biss.ctf.backend.configuration.CTFRoute
import biss.ctf.backend.configuration.RoutingConfiguration
import biss.ctf.backend.objects.apiObjects.Megama
import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.services.UserDataService
import biss.ctf.backend.services.UserStageService
import jakarta.persistence.EntityNotFoundException
import mu.KotlinLogging
import org.springframework.stereotype.Service
import javax.naming.ConfigurationException

/**
 * A service with the stage routing logic for a user.
 * This is what enables us to have multiple CTF paths for multiple megamot.
 */
@Service
class StageRouter(
    private val routingConfiguration: RoutingConfiguration,
    private val userStageService: UserStageService,
    private val userDataService: UserDataService
) {
    companion object {
        val logger = KotlinLogging.logger(StageRouter::class.java.name)
    }

    /**
     * Gets the corresponding login page for a megama.
     *
     * @param megama The megama to get the login page for
     * @return The login stage.
     */
    fun getLoginStage(megama: Megama): CTFStage {
        if (megama !in routingConfiguration.routingPaths.keys) {
            logger.error("Megama ${megama.name} not mapped to a routing path!")
            throw ConfigurationException("Megama ${megama.name} not mapped to a routing path!")
        }

        return this.routingConfiguration.routingPaths[megama]!!.first()
    }

    /**
     * Gets the next stage for a given user, according to his CTF path.
     *
     * @param uuid UUID of the user that requests the next stage.
     * @return The user's next stage.
     */
    fun getUserNextStage(uuid: String): CTFStage {
        val user = userDataService.findUserByUuid(uuid)
            ?: throw NoSuchElementException("Attempted to find megama of user with uuid '$uuid', but the user doesn't exist")

        return this.userStageService.findStageByUuid(uuid)?.getNextStage(user.megama) ?: run {
            logger.error("No current stage is saved for user with uuid $uuid!")
            throw EntityNotFoundException("No current stage is saved for user with uuid $uuid!")
        }
    }

    /**
     * Gets the next CTF stage for a megama's path.
     *
     * @param megama The megama for which we want the next stage.
     */
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