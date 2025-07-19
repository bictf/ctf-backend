package biss.ctf.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BissCtfBackApplication

fun main(args: Array<String>) {
    runApplication<BissCtfBackApplication>(*args)
}
