package biss.ctf.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BissCtfBackApplication

//TODO(98) add exception that says (you shouldn't be here! this is not for your profession or smth)
fun main(args: Array<String>) {
    runApplication<BissCtfBackApplication>(*args)
}
