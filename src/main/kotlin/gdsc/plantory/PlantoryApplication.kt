package gdsc.plantory

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.TimeZone
import javax.annotation.PostConstruct

@SpringBootApplication
class PlantoryApplication

fun main(args: Array<String>) {
    runApplication<PlantoryApplication>(*args)

    @PostConstruct
    fun setTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }
}
