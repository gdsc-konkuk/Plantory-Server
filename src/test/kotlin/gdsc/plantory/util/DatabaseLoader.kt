package gdsc.plantory.util

import gdsc.plantory.fixture.CompanionPlantFixture
import gdsc.plantory.fixture.MemberFixture
import gdsc.plantory.fixture.PlantInformationFixture
import gdsc.plantory.member.domain.MemberRepository
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DatabaseLoader(
    private val memberRepository: MemberRepository,
    private val plantInformationRepository: PlantInformationRepository,
    private val companionPlantRepository: CompanionPlantRepository,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)!!
    }

    fun loadData() {
        log.info("[call DataLoader]")

        val testMember = MemberFixture.generateTestMember(1L)
        val testPlantInformation = PlantInformationFixture.generateTestPlantInformation(1L)
        val testCompanionPlantWillHaveHistories = CompanionPlantFixture.generateTestCompanionPlantWillHaveHistories(1L)
        val testCompanionPlantHasNoHistories = CompanionPlantFixture.generateTestCompanionPlantHasNoHistories(2L)

        testCompanionPlantWillHaveHistories.saveRecord("test-record", "https://test.com")
        testCompanionPlantWillHaveHistories.saveHistory(HistoryType.RECORDING)
        testCompanionPlantWillHaveHistories.saveHistory(HistoryType.POT_CHANGE)
        testCompanionPlantWillHaveHistories.saveHistory(HistoryType.WATER_CHANGE)

        memberRepository.save(testMember)
        plantInformationRepository.save(testPlantInformation)
        companionPlantRepository.saveAll(listOf(testCompanionPlantWillHaveHistories, testCompanionPlantHasNoHistories))

        log.info("[init complete DataLoader]")
    }
}
