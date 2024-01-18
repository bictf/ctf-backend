package biss.ctf.back.services

import biss.ctf.back.entities.MazeLevelEntity
import biss.ctf.back.repositories.MazeRepository
import biss.ctf.back.repositories.UserDataRepository
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