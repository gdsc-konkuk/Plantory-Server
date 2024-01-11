package gdsc.plantory.plant.presentation.dto

import jakarta.validation.constraints.PastOrPresent
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class CompanionPlantCreateRequest(

    var plantInformationId: Long,
    val nickname: String,
    val shortDescription: String,

    @PastOrPresent(message = "입양일은 과거 또는 현재의 날짜여야 합니다. birthDate: \${validatedValue}")
    @DateTimeFormat(pattern = "yyyy-MM-dd") val birthDate: LocalDate,

    @PastOrPresent(message = "마지막 물주기 날짜는 과거 또는 현재의 날짜여야 합니다. lastWaterDate: \${validatedValue}")
    @DateTimeFormat(pattern = "yyyy-MM-dd") val lastWaterDate: LocalDate,
)