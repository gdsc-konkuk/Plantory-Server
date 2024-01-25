package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.PlantInformationStep.Companion.식물_정보_조회_요청
import gdsc.plantory.acceptance.PlantInformationStep.Companion.식물_정보_조회_응답_확인
import org.junit.jupiter.api.Test

class PlantInformationAcceptanceTest {

    @Test
    fun `식물 정보 조회`() {
        // when
        val 식물_정보_조회_요청_응답 = 식물_정보_조회_요청()

        // then
        식물_정보_조회_응답_확인(식물_정보_조회_요청_응답)
    }
}
