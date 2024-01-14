package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_등록_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_조회_요청
import gdsc.plantory.fixture.CompanionPlantFixture
import gdsc.plantory.plant.presentation.dto.CompanionPlantLookupRequest
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

    @Test
    fun `사용자의 반려식물 조회`() {
        // given
        val lookupRequest = CompanionPlantLookupRequest(0L);

        // when
        val 식물_조회_요청_응답 = 반려_식물_조회_요청(lookupRequest, "device_id")

        // then
        CommonStep.응답_확인(식물_조회_요청_응답, HttpStatus.OK)
    }
}
