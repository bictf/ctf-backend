package biss.ctf.backend.entities

import biss.ctf.backend.objects.routing.CTFStage
import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
data class UserStageEntity(
    @Id
    val uuid: String,
    val stage: CTFStage,
)