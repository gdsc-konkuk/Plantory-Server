package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.CommonStep.Companion.응답_확인
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_등록_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_조회_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.데일리_기록_등록_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_히스토리_생성_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_조회_응답_확인
import gdsc.plantory.fixture.CompanionPlantFixture.generateCompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordCreateRequest
import gdsc.plantory.plant.presentation.dto.CompanionPlantHistoryRequest
import gdsc.plantory.util.AcceptanceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

@DisplayName("인수 : CompanionPlant")
class CompanionPlantAcceptanceTest : AcceptanceTest() {

    @Test
    fun `반려식물 등록`() {
        // given
        val 반려_식물_정보 = generateCompanionPlantCreateRequest("퐁퐁이")

        // when
        val 식물_등록_요청_응답 = 반려_식물_등록_요청(반려_식물_정보, "device-token")

        // then
        응답_확인(식물_등록_요청_응답, HttpStatus.OK)
    }

    @Test
    fun `반려식물 물주기 히스토리 등록`() {
        // given
        val 물줌_기록 = CompanionPlantHistoryRequest(1L, "WATER_CHANGE")

        // when
        val 식물_히스토리_생성_응답 = 식물_히스토리_생성_요청(물줌_기록, "device-token")

        // then
        응답_확인(식물_히스토리_생성_응답, HttpStatus.OK)
    }

    @Test
    fun `반려식물 조회`() {
        // when
        val 식물_조회_요청_응답 = 식물_조회_요청("device-token")

        // then
        식물_조회_응답_확인(식물_조회_요청_응답)
    }

    @Test
    fun `반려식물 데일리 기록 등록`() {
        // given
        val 데일리_기록_정보 = PlantRecordCreateRequest(1L, "오늘도 행복한 하루!")

        // when
        val 데일리_기록_등록_요청_응답 = 데일리_기록_등록_요청(데일리_기록_정보, "device-token")

        // then
        응답_확인(데일리_기록_등록_요청_응답, HttpStatus.OK)
    }
}
