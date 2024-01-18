package biss.ctf.back.objects.apiObjects.toUser

import biss.ctf.back.entities.MazeLevelEntity

data class LevelResponseToUser(
    val levelId: String,
    val question: String,
    val answers: List<String>,
    val passwordPlace: String,
){
    constructor(level: MazeLevelEntity): this(
        level.sha256,
        level.question,
        (level.wrongAnswers + level.rightAnswer).shuffled(),
        level.passwordPlace
    )
}
