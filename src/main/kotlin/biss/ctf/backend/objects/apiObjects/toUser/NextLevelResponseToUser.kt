package biss.ctf.back.objects.apiObjects.toUser

import biss.ctf.back.entities.MazeLevelEntity

open class NextLevelResponseToUser(
    val nextLevelId: String
) {
    constructor(level: MazeLevelEntity) : this(
        level.sha256
    )
}
