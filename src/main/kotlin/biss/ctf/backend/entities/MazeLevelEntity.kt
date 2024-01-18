package biss.ctf.back.entities

import java.security.MessageDigest

data class MazeLevelEntity(
    val question: String,
    val rightAnswer: String,
    val wrongAnswers: List<String>,
    val password: String,
    val passwordPlace: String
){
    private val md = MessageDigest.getInstance("SHA-256")
    public val sha256: String = md.digest(question.toByteArray()).fold("") { str, it -> str + "%02x".format(it) }
}