package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.PlantInformationStep.Companion.식물_정보_조회_요청
import gdsc.plantory.acceptance.PlantInformationStep.Companion.식물_정보_조회_응답_확인
import gdsc.plantory.util.AcceptanceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("인수 : PlantInformation")
class PlantInformationAcceptanceTest : AcceptanceTest() {

    @Test
    fun `식물 정보 조회`() {
        // when
        val 식물_정보_조회_요청_응답 = 식물_정보_조회_요청()

        // then
        식물_정보_조회_응답_확인(식물_정보_조회_요청_응답)
    }
}
