package gdsc.plantory.fixture

import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import java.time.LocalDate

private var _기록없는_테스트식물_ID = 0L
val 기록없는_테스트식물_ID
    get() = _기록없는_테스트식물_ID

private var _기록있는_테스트식물_ID = 0L
val 기록있는_테스트식물_ID
    get() = _기록있는_테스트식물_ID


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

    fun generateTestCompanionPlantHasNoHistories(id: Long): CompanionPlant {
        _기록없는_테스트식물_ID = id

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
            id = id
        )
    }

    fun generateTestCompanionPlantWillHaveHistories(id: Long): CompanionPlant {
        _기록있는_테스트식물_ID = id

        return CompanionPlant(
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _shortDescription = "덕구리난은 덕구리난과!",
            _nickname = "히스토리 있는 테스트식물",
            birthDate = LocalDate.of(2024, 1, 1),
            nextWaterDate = LocalDate.of(2024, 1, 10),
            lastWaterDate = LocalDate.of(2024, 1, 7),
            waterCycle = 3,
            plantInformationId = 테스트_식물정보_ID,
            memberId = 테스터_ID,
            id = id
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
