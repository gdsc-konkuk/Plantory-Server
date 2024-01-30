package gdsc.plantory.plant.service

import gdsc.plantory.fixture.CompanionPlantFixture.generateCompanionPlant
import gdsc.plantory.fixture.PlantInformationFixture.generatePlantInformation
import gdsc.plantory.fixture.테스터_디바이스_토큰
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import gdsc.plantory.util.AcceptanceTest
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@DisplayName("서비스 : PlantService")
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
        val plantInformation = generatePlantInformation()
        val savedPlantInformation = plantInformationRepository.save(plantInformation)

        val today = LocalDate.now()
        val companionPlant = generateCompanionPlant(memberId = 1L, plantInformationId = savedPlantInformation.getId)
        companionPlant.saveRecord("test-record", "https://test.com", today)
        companionPlant.saveHistory(HistoryType.WATER_CHANGE, today)
        val savedPlant = companionPlantRepository.save(companionPlant)

        entityManager.flush()
        entityManager.clear()

        // when
        val result = plantService.lookupPlantRecordOfDate(
            savedPlant.getId, today, 테스터_디바이스_토큰
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
        val plantInformation = generatePlantInformation()
        val savedPlantInformation = plantInformationRepository.save(plantInformation)

        val companionPlant = generateCompanionPlant(memberId = 1L, plantInformationId = savedPlantInformation.getId)
        val today = LocalDate.now()
        companionPlant.saveRecord("test-record", "https://test.com", today)
        companionPlant.saveHistory(HistoryType.POT_CHANGE, today)
        val savedPlant = companionPlantRepository.save(companionPlant)

        entityManager.flush()
        entityManager.clear()

        // when
        val result = plantService.lookupPlantRecordOfDate(
            savedPlant.getId, today, 테스터_디바이스_토큰
        )

        // then
        assertAll(
            { assertThat(result.comment).isEqualTo("test-record") },
            { assertThat(result.imageUrl).isEqualTo("https://test.com") },
            { assertThat(result.water).isFalse() },
        )
    }
}
