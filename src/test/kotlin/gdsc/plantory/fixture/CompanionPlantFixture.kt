package gdsc.plantory.fixture

import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import java.time.LocalDate

object CompanionPlantFixture {

    var 덕구리난: CompanionPlant = CompanionPlant(
        _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
        _shortDescription = "덕구리난은 덕구리난과!",
        _nickname = "shine",
        birthDate = LocalDate.of(2024, 1, 1),
        nextWaterDate = LocalDate.of(2024, 1, 10),
        lastWaterDate = LocalDate.of(2024, 1, 7),
        waterCycle = 3,
        plantInformationId = 1L,
        memberId = 1L,
    )

    fun generatePetPlantCreateRequest(plantInformationId: Long): CompanionPlantCreateRequest {
        return CompanionPlantCreateRequest(
            plantInformationId = plantInformationId,
            nickname = "퐁퐁이",
            shortDescription = "퐁퐁이는 선인장 입니다!",
            birthDate = LocalDate.of(2024, 3, 8),
            lastWaterDate = LocalDate.of(2024, 3, 5),
        )
    }
}
