package biss.ctf.backend.objects.apiObjects.toUser

import biss.ctf.backend.entities.MazeLevelEntity

open class NextLevelResponseToUser(
    val nextLevelId: String
) {
    constructor(level: MazeLevelEntity) : this(
        level.sha256
    )
}
