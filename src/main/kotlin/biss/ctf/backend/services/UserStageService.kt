package biss.ctf.backend.services

import biss.ctf.backend.entities.UserStageEntity
import biss.ctf.backend.objects.routing.CTFStage
import biss.ctf.backend.repositories.UserStageRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

/**
 * A service to manage the user stage storage and accessing
 */
@Service
class UserStageService(private val userStageRepository: UserStageRepository) {
    /**
     * Gets a user's stage by its UUID, or null if it can't find it.
     */
    fun findStageByUuid(uuid: String): CTFStage? = userStageRepository.findById(uuid).getOrNull()?.stage

    /**
     * Saves a user's stage, updates if already exists with another stage.
     */
    fun saveUserStage(uuid: String, stage: CTFStage): CTFStage =
        userStageRepository.save(UserStageEntity(uuid, stage)).stage
}