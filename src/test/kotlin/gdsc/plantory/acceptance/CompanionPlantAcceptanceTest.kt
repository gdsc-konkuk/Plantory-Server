package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.CommonStep.Companion.응답_확인
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_등록_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_조회_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_히스토리_생성_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.조회_응답_확인
import gdsc.plantory.acceptance.MemberStep.Companion.회원_가입_요청
import gdsc.plantory.fixture.CompanionPlantFixture
import gdsc.plantory.member.dto.MemberSignUpRequest
import gdsc.plantory.plant.presentation.dto.CompanionPlantHistoryRequest
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
        val 식물_등록_요청_응답 = 반려_식물_등록_요청(createRequest, "device-token")

        // then
        응답_확인(식물_등록_요청_응답, HttpStatus.OK)
    }

    @Test
    fun `반려식물 물주기 히스토리 등록`() {
        // given
        val 물줌_기록 = CompanionPlantHistoryRequest(1L, "WATER_CHANGE")

        // when
        val 식물_히스토리_생성_응답 = 반려_식물_히스토리_생성_요청(물줌_기록, "device-token")

        // then
        응답_확인(식물_히스토리_생성_응답, HttpStatus.OK)
    }

    @Test
    fun `사용자의 반려식물 조회`() {
        // given
        회원_가입_요청(MemberSignUpRequest("device-token"))

        // when
        val 식물_조회_요청_응답 = 반려_식물_조회_요청("device-token")

        // then
        조회_응답_확인(식물_조회_요청_응답)
    }
}
