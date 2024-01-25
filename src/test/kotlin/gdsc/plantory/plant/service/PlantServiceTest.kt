package gdsc.plantory.plant.service

import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupRequest
import gdsc.plantory.plantInformation.domain.PlantInformation
import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import gdsc.plantory.util.AcceptanceTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Transactional
class PlantServiceTest(
    @Autowired val plantService: PlantService,
    @Autowired val companionPlantRepository: CompanionPlantRepository,
    @Autowired val entityManager: EntityManager,
    @Autowired val plantInformationRepository: PlantInformationRepository,
) : AcceptanceTest() {

    @Test
    fun `사용자는 식물의 데일리 기록을 조회하여 사진, 본문, 물준유무를 확인할 수 있다`() {
        // given
        val plantInformation = PlantInformation(
            _species = "덕구리난",
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _familyName = "백합과",
            smell = "거의 없음",
            poison = "없음",
            manageLevel = "초보자",
            growSpeed = "느림",
            _requireTemp = "21~25℃",
            _minimumTemp = "13℃ 이상",
            requireHumidity = "40% 미만",
            postingPlace = "거실 창측 (실내깊이 150~300cm),발코니 내측 (실내깊이 50~150cm),발코니 창측 (실내깊이 0~50cm)",
            specialManageInfo = "적절한 환기가 필요함, 여름동안 햇볕이 잘드는 위치에 배치하는 것이 좋음.",
            _waterCycleSpring = 4,
            _waterCycleSummer = 3,
            _waterCycleAutumn = 4,
            _waterCycleWinter = 4,
        )
        val savedPlantInformation = plantInformationRepository.save(plantInformation)

        val today = LocalDate.now()
        val companionPlant = CompanionPlant(
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _shortDescription = "덕구리난은 덕구리난과!",
            _nickname = "shine",
            birthDate = LocalDate.of(2024, 1, 1),
            nextWaterDate = today,
            lastWaterDate = LocalDate.of(2024, 1, 23),
            waterCycle = 3,
            plantInformationId = savedPlantInformation.getId,
            memberId = 1L,
        )
        companionPlant.saveRecord("test-record", "https://test.com", today)
        companionPlant.saveHistory(HistoryType.RECORDING, today)
        companionPlant.saveHistory(HistoryType.WATER_CHANGE, today)
        val savedPlant = companionPlantRepository.save(companionPlant)

        entityManager.flush()
        entityManager.clear()

        // when
        val result = plantService.lookupPlantRecordOfDate(
            PlantRecordLookupRequest(savedPlant.getId, today), "device-token"
        )

        // then
        assertAll(
            { assertThat(result.comment).isEqualTo("test-record") },
            { assertThat(result.imageUrl).isEqualTo("https://test.com") },
            { assertThat(result.water).isTrue() },
        )
    }

    @Test
    fun `사용자는 식물의 데일리 기록을 조회했을때 물을 주지 않았던 경우 기록에 물주는 표시가 비어있다`() {
        // given
        val plantInformation = PlantInformation(
            _species = "덕구리난",
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _familyName = "백합과",
            smell = "거의 없음",
            poison = "없음",
            manageLevel = "초보자",
            growSpeed = "느림",
            _requireTemp = "21~25℃",
            _minimumTemp = "13℃ 이상",
            requireHumidity = "40% 미만",
            postingPlace = "거실 창측 (실내깊이 150~300cm),발코니 내측 (실내깊이 50~150cm),발코니 창측 (실내깊이 0~50cm)",
            specialManageInfo = "적절한 환기가 필요함, 여름동안 햇볕이 잘드는 위치에 배치하는 것이 좋음.",
            _waterCycleSpring = 4,
            _waterCycleSummer = 3,
            _waterCycleAutumn = 4,
            _waterCycleWinter = 4,
        )
        val savedPlantInformation = plantInformationRepository.save(plantInformation)

        val companionPlant = CompanionPlant(
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _shortDescription = "덕구리난은 덕구리난과!",
            _nickname = "shine",
            birthDate = LocalDate.of(2024, 1, 1),
            nextWaterDate = LocalDate.of(2024, 1, 25),
            lastWaterDate = LocalDate.of(2024, 1, 23),
            waterCycle = 3,
            plantInformationId = savedPlantInformation.getId,
            memberId = 1L,
        )
        val today = LocalDate.now()
        companionPlant.saveRecord("test-record", "https://test.com", today)
        companionPlant.saveHistory(HistoryType.RECORDING, today)
        companionPlant.saveHistory(HistoryType.POT_CHANGE, today)
        val savedPlant = companionPlantRepository.save(companionPlant)

        entityManager.flush()
        entityManager.clear()

        // when
        val result = plantService.lookupPlantRecordOfDate(
            PlantRecordLookupRequest(savedPlant.getId, today), "device-token"
        )

        // then
        assertAll(
            { assertThat(result.comment).isEqualTo("test-record") },
            { assertThat(result.imageUrl).isEqualTo("https://test.com") },
            { assertThat(result.water).isFalse() },
        )
    }
}
