package gdsc.plantory.util

import gdsc.plantory.member.domain.Member
import gdsc.plantory.member.domain.MemberRepository
import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plantInformation.domain.PlantInformation
import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDate

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

        val member = memberRepository.save(Member("device-token", 1L))

        val plantInformation = plantInformationRepository.save(
            PlantInformation(
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
                id = 1L,
            )
        )

        val companionPlant1 = CompanionPlant(
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _shortDescription = "덕구리난은 덕구리난과!",
            _nickname = "덕구리난1",
            nextWaterDate = LocalDate.now().plusDays(3),
            lastWaterDate = LocalDate.now(),
            waterCycle = 3,
            plantInformationId = plantInformation.getId,
            memberId = member.getId,
            id = 1L,
        )

        val companionPlant2 = CompanionPlant(
            _imageUrl = "https://nongsaro.go.kr/cms_contents/301/13336_MF_ATTACH_05.jpg",
            _shortDescription = "덕구리난은 덕구리난과!",
            _nickname = "덕구리난2",
            nextWaterDate = LocalDate.now().plusDays(3),
            lastWaterDate = LocalDate.now(),
            waterCycle = 3,
            plantInformationId = plantInformation.getId,
            memberId = member.getId,
            id = 2L,
        )

        companionPlant2.saveRecord("test-record2", "https://test.com")

        companionPlantRepository.saveAll(listOf(companionPlant1, companionPlant2))

        log.info("[init complete DataLoader]")
    }
}
