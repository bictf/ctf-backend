package biss.ctf.back.entities

import java.io.File

data class FileEntity(
    val title: String,
    val content: String,
    val isBinaryFile: Boolean = false,
    val file: File = File("")
)
