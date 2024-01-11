package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.CompanionPlantAcceptanceStep.Companion.반려_식물_등록_요청
import gdsc.plantory.fixture.CompanionPlantFixture
import gdsc.plantory.util.AcceptanceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("인수 : CompanionPlant")
class CompanionPlantAcceptanceTest : AcceptanceTest() {

    @Test
    fun `사용자의 반려 식물 등록`() {
        // given
        val createRequest = CompanionPlantFixture.generatePetPlantCreateRequest(1L)

        // when
        val 식물_등록_요청_응답 = 반려_식물_등록_요청(createRequest, "device_id")

        // then
        CommonStep.응답_확인(식물_등록_요청_응답, HttpStatus.OK)
    }
}
