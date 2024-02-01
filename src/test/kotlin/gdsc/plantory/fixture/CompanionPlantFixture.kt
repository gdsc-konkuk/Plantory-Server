package gdsc.plantory.fixture

import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import java.time.LocalDate

object CompanionPlantFixture {

    fun generateCompanionPlant(
        memberId: Long = 0L,
        plantInformationId: Long = 0L,
        imageUrl: String = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
        shortDescription: String = "덕구리난은 덕구리난과!",
        nickname: String = "테스트 식물",
        nextWaterDate: LocalDate = LocalDate.of(2024, 1, 10),
        lastWaterDate: LocalDate = LocalDate.of(2024, 1, 7),
        waterCycle: Int = 3,
        birthDate: LocalDate = LocalDate.of(2024, 1, 1),
    ): CompanionPlant {
        return CompanionPlant(
            _imageUrl = imageUrl,
            _shortDescription = shortDescription,
            _nickname = nickname,
            nextWaterDate = nextWaterDate,
            lastWaterDate = lastWaterDate,
            waterCycle = waterCycle,
            birthDate = birthDate,
            memberId = memberId,
            plantInformationId = plantInformationId,
        )
    }

    fun generateCompanionPlantCreateRequest(plantInformationId: Long): CompanionPlantCreateRequest {
        return CompanionPlantCreateRequest(
            nickname = "퐁퐁이",
            plantInformationId = plantInformationId,
            shortDescription = "퐁퐁이는 선인장 입니다!",
            birthDate = LocalDate.of(2024, 3, 8),
            lastWaterDate = LocalDate.of(2024, 3, 5),
        )
    }
}
