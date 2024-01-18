package biss.ctf.back.controllers

import biss.ctf.back.objects.apiObjects.toUser.LevelResponseToUser
import biss.ctf.back.objects.apiObjects.toUser.LootResponseToUser
import biss.ctf.back.objects.apiObjects.toUser.NextLevelResponseToUser
import biss.ctf.back.objects.apiObjects.toUser.TrashResponseToUser
import biss.ctf.back.services.MazeService
import biss.ctf.back.services.UserDataService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/maze")
class MazeController(
    val mazeService: MazeService,
    val userDataService: UserDataService,

    ) {
    val FIRST_LEVEL = mazeService.getFirstLevel()
    val LAST_LEVEL = mazeService.getLastLevel()

    @GetMapping("/level")
    fun gelLevel(@RequestParam id: String? = ""): LevelResponseToUser {
        val level = mazeService.getLevelByID(id ?: "")
        return LevelResponseToUser(level)
    }

    @PostMapping("/level")
    fun submitAnswer(
        @RequestParam uuid: String,
        @RequestParam id: String,
        @RequestParam answer: String,
        @RequestParam password: String
    ): NextLevelResponseToUser {
        val level = mazeService.getLevelByID(id)

        if (level.password != password) {
            throw Exception("wrong password, try again!")
        }

        userDataService.userCompletedLevel(uuid, id, level.rightAnswer == answer)

        if (id == LAST_LEVEL.sha256) {
            return if (userDataService.doesUserCompletedLevels(uuid)) {
                LootResponseToUser()
            } else {
                TrashResponseToUser()
            }
        }

        val nextLevel = mazeService.getNextLevelByID(id)
        return NextLevelResponseToUser(nextLevel)
    }

    @ExceptionHandler(Exception::class)
    fun customerNotFound(exception: Exception): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(exception.message)
    }
}