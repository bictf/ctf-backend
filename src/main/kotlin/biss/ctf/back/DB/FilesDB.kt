package biss.ctf.back.DB

import biss.ctf.back.entities.FileEntity

val FILES_DB = arrayOf(
    FileEntity("bla", "yey\nwhoooo\nuuuuuuu\nuuuu\nuuu\nuuuuuu\nuuuuuuuuuu\nuuuuuuu\nuuuuuuuuu\nuuuuuuuuuuuuuuuu\nuuuuuuuuu\nuuuuu\nuuuuu\nuuu\nuuuu\nuuu\nuu\ngg"),
    FileEntity("bla1", "boo"),
    FileEntity("bla2", "biii"),
    FileEntity("bla3", "blaa"),
    FileEntity("bla4", "bolo"),
    FileEntity("bli", "bolo"),
    FileEntity("Top Secret File", "??????????", isAdminFile=true)
    )