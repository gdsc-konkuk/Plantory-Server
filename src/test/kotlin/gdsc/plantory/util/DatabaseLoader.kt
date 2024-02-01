package gdsc.plantory.util

import gdsc.plantory.fixture.CompanionPlantFixture.generateCompanionPlant
import gdsc.plantory.fixture.MemberFixture.generateMember
import gdsc.plantory.fixture.PlantInformationFixture.generatePlantInformation
import gdsc.plantory.fixture.테스터_디바이스_토큰
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

        val testMember = generateMember(deviceToken = 테스터_디바이스_토큰)
        val savedTestMember = memberRepository.save(testMember)

        val testPlantInformation = generatePlantInformation()
        val savedTestPlantInformation = plantInformationRepository.save(testPlantInformation)

        val testCompanionPlantHasHistories = generateCompanionPlant(
            memberId = savedTestMember.getId,
            plantInformationId = savedTestPlantInformation.getId,
        )
        testCompanionPlantHasHistories.saveRecord("test-record", "https://test.com")
        testCompanionPlantHasHistories.saveHistory(HistoryType.POT_CHANGE)
        testCompanionPlantHasHistories.saveHistory(HistoryType.WATER_CHANGE)
        val savedTestCompanionPlantHasHistories = companionPlantRepository.save(testCompanionPlantHasHistories)

        val testCompanionPlantHasNoHistories = generateCompanionPlant(
            memberId = savedTestMember.getId,
            plantInformationId = savedTestPlantInformation.getId,
        )
        val savedTestCompanionPlantHasNoHistories = companionPlantRepository.save(testCompanionPlantHasNoHistories)

        log.info("[init complete DataLoader]")
    }
}
