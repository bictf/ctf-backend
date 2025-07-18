package biss.ctf.backend.objects.wordle

import com.fasterxml.jackson.annotation.JsonFormat

@JsonFormat(shape = JsonFormat.Shape.NUMBER)
enum class WordleCharState {
    IncorrectChar,
    CorrectCharWrongPlace,
    CorrectCharCorrectPlace
}