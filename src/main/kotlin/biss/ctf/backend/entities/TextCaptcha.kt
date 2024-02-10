package biss.ctf.backend.entities

import java.nio.file.Path

data class TextCaptcha (
    val image: Path,
    val solution: String
)