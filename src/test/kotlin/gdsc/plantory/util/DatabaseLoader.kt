package gdsc.plantory.util

import gdsc.plantory.fixture.CompanionPlantFixture
import gdsc.plantory.fixture.MemberFixture
import gdsc.plantory.fixture.PlantInformationFixture
import gdsc.plantory.member.domain.MemberRepository
import gdsc.plantory.plant.domain.CompanionPlantRepository
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

        memberRepository.save(MemberFixture.generateTestMember())
        plantInformationRepository.save(PlantInformationFixture.generateTestPlantInformation())
        companionPlantRepository.saveAll(
            listOf(
                CompanionPlantFixture.generateTestCompanionPlantWithoutHistories(),
                CompanionPlantFixture.generateTestCompanionPlantWithHistories(),
            )
        )

        log.info("[init complete DataLoader]")
    }
}
