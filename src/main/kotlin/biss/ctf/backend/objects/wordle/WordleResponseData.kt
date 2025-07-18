package biss.ctf.backend.objects.wordle

import biss.ctf.backend.services.login.WordleDiff

data class WordleResponseData(
    val wordleDiff: WordleDiff,
    val time: Long
)