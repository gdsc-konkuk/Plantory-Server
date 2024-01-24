package gdsc.plantory.plant.presentation.dto

import gdsc.plantory.plant.domain.CompanionPlant
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
) {
    fun toEntity(imagePath: String, memberId: Long, waterCycle: Int): CompanionPlant {
        // TODO : Cloud 환경으로 이전 후 제거, 로컬 사진 저장 테스트 용도
        val baseUrl: String = "https://nongsaro.go.kr/"
        return CompanionPlant(
            _imageUrl = baseUrl + imagePath,
            _shortDescription = this.shortDescription,
            _nickname = this.nickname,
            nextWaterDate = this.lastWaterDate.plusDays(waterCycle.toLong()),
            lastWaterDate = this.lastWaterDate,
            waterCycle = waterCycle,
            birthDate = this.birthDate,
            memberId = memberId,
        )
    }
}
