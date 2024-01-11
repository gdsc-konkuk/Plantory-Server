package gdsc.plantory.util

import gdsc.plantory.member.domain.Member
import gdsc.plantory.member.domain.MemberRepository
import gdsc.plantory.plantInformation.domain.PlantInformation
import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

private const val DEFAULT_FOLDER_NAME = "default"

@Component
class DatabaseLoader(
    private val memberRepository: MemberRepository,
    private val plantInformationRepository: PlantInformationRepository,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!
    }

    fun loadData() {
        log.info("[call DataLoader]")

        memberRepository.save(Member("device_id"))

        plantInformationRepository.save(
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

        log.info("[init complete DataLoader]")
    }
}
