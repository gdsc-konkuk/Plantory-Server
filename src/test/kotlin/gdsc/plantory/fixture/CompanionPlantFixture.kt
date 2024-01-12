package gdsc.plantory.fixture

import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import java.time.LocalDate

object CompanionPlantFixture {

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
