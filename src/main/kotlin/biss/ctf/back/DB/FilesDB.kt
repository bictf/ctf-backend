package biss.ctf.back.DB

import biss.ctf.back.entities.FileEntity
import java.io.File

val FILES_DB = arrayOf(
    FileEntity("bla0", "yey\nwhoooo\nuuuuuuu\nuuuu\nuuu\nuuuuuu\nuuuuuuuuuu\nuuuuuuu\nuuuuuuuuu\nuuuuuuuuuuuuuuuu\nuuuuuuuuu\nuuuuu\nuuuuu\nuuu\nuuuu\nuuu\nuu\ngg"),
    FileEntity("bla1", "boo"),
    FileEntity("bla2", "biii"),
    FileEntity("bla3", "blaa"),
    FileEntity("bla4", "bolo"),
    FileEntity("bli", "bolo"),
    FileEntity("Tob Secret File", "?????????????????", isBinaryFile=true, file= File("src/main/resources/loot/loot.zip")),
)