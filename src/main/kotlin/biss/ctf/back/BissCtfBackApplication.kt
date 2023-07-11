package biss.ctf.back

import biss.ctf.back.repositories.MazeRepository
import biss.ctf.back.services.ZipMazeService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BissCtfBackApplication

fun main(args: Array<String>) {

    ZipMazeService(MazeRepository()).createZipMaze()

    runApplication<BissCtfBackApplication>(*args)
}
