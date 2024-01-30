package biss.ctf.backend.services

import biss.ctf.backend.entities.MazeLevelEntity
import biss.ctf.backend.repositories.MazeRepository
import org.springframework.stereotype.Service

@Service
class MazeService(
    val mazeRepository: MazeRepository
) {
    fun getLevelByID(levelId: String): MazeLevelEntity {
        return mazeRepository.get(levelId)
    }

    fun getNextLevelByID(levelId: String): MazeLevelEntity {
        return mazeRepository.getNext(levelId)
    }

    fun getLastLevel(): MazeLevelEntity {
        return mazeRepository.getLast()
    }

    fun getFirstLevel(): MazeLevelEntity {
        return mazeRepository.getFirst()
    }
}