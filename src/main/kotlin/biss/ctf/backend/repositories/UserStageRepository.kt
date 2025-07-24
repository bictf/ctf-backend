package biss.ctf.backend.repositories

import biss.ctf.backend.entities.UserStageEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

/**
 * Maps between a user's UUID and the current stage he is in.
 */
@Repository
interface UserStageRepository : CrudRepository<UserStageEntity, String> {
}