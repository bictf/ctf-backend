package biss.ctf.back.repositories

import biss.ctf.back.DB.MAZE_LEVELS_DB
import biss.ctf.back.entities.MazeLevelEntity
import org.springframework.stereotype.Repository

@Repository
class MazeRepository {
    fun get(levelId: String): MazeLevelEntity {
        return MAZE_LEVELS_DB.find { it.sha256 == levelId } ?: MAZE_LEVELS_DB[0]
    }

    fun getNext(levelId: String): MazeLevelEntity {
        var flag = false
        for (level in MAZE_LEVELS_DB){
            if (flag){
                return level
            }

            if (level.sha256 == levelId){
                flag = true
            }
        }

        return MAZE_LEVELS_DB[1]
    }

    fun getLast(): MazeLevelEntity {
        return MAZE_LEVELS_DB.last()
    }

    fun getFirst(): MazeLevelEntity {
        return MAZE_LEVELS_DB.first()
    }
}