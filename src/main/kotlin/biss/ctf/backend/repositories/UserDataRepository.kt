package biss.ctf.backend.repositories

import biss.ctf.backend.entities.UserDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDataRepository : CrudRepository<UserDataEntity, String>