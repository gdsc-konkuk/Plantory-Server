package gdsc.plantory.fixture

import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordCreateRequest
import java.time.LocalDate

const val 기록없는_테스트식물_ID = 1L
const val 기록있는_테스트식물_ID = 2L

object CompanionPlantFixture {

    val 덕구리난: CompanionPlant = CompanionPlant(
        _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
        _shortDescription = "덕구리난은 덕구리난과!",
        _nickname = "덕구리1",
        birthDate = LocalDate.of(2024, 1, 1),
        nextWaterDate = LocalDate.of(2024, 1, 10),
        lastWaterDate = LocalDate.of(2024, 1, 7),
        waterCycle = 3,
        plantInformationId = 테스트_식물정보_ID,
        memberId = 테스터_ID,
    )

    fun generateTestCompanionPlantWithoutHistories(): CompanionPlant {
        return CompanionPlant(
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _shortDescription = "덕구리난은 덕구리난과!",
            _nickname = "히스토리 없는 테스트식물",
            birthDate = LocalDate.of(2024, 1, 1),
            nextWaterDate = LocalDate.of(2024, 1, 10),
            lastWaterDate = LocalDate.of(2024, 1, 7),
            waterCycle = 3,
            plantInformationId = 테스트_식물정보_ID,
            memberId = 테스터_ID,
            id = 기록없는_테스트식물_ID
        )
    }

    fun generateTestCompanionPlantWithHistories(): CompanionPlant {
        val companionPlant = CompanionPlant(
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _shortDescription = "덕구리난은 덕구리난과!",
            _nickname = "히스토리 있는 테스트식물",
            birthDate = LocalDate.of(2024, 1, 1),
            nextWaterDate = LocalDate.of(2024, 1, 10),
            lastWaterDate = LocalDate.of(2024, 1, 7),
            waterCycle = 3,
            plantInformationId = 테스트_식물정보_ID,
            memberId = 테스터_ID,
            id = 기록있는_테스트식물_ID
        )

        companionPlant.saveRecord("test-record", "https://test.com")
        companionPlant.saveHistory(HistoryType.RECORDING)
        companionPlant.saveHistory(HistoryType.POT_CHANGE)
        companionPlant.saveHistory(HistoryType.WATER_CHANGE)

        return companionPlant
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

    fun generatePlantRecordCreateRequest(companionPlantId: Long): PlantRecordCreateRequest {
        return PlantRecordCreateRequest(
            companionPlantId = companionPlantId,
            comment = "오늘도 즐거운 하루~!"
        )
    }
}
