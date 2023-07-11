package biss.ctf.back.entities

data class MazeLevelEntity(
    val readmeText: String,
    val rightAnswer: String,
    val wrongAnswers: List<String>,
    val password: String
)
