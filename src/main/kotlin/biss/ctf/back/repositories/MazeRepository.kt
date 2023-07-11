package biss.ctf.back.repositories

import biss.ctf.back.DB.MAZE_LEVELS_DB
import org.springframework.stereotype.Repository

@Repository
class MazeRepository {
    constructor(){
        MAZE_LEVELS_DB.reverse()
    }

    fun getLevels() = MAZE_LEVELS_DB
}