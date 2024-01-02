package gdsc.plantory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PlantoryApplication

fun main(args: Array<String>) {
    runApplication<PlantoryApplication>(*args)
}
