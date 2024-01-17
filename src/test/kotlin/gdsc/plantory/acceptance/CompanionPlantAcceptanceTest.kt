package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.CommonStep.Companion.응답_확인
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_등록_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_조회_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.데일리_기록_등록_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.데일리_기록_조회_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.데일리_기록_조회_응답_확인
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_히스토리_생성_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_조회_응답_확인
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.히스토리_조회_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.히스토리_조회_응답_확인
import gdsc.plantory.fixture.CompanionPlantFixture.generateCompanionPlantCreateRequest
import gdsc.plantory.fixture.CompanionPlantFixture.generatePlantRecordCreateRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoryRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupRequest
import gdsc.plantory.util.AcceptanceTest
import gdsc.plantory.util.TEST_MEMBER_TOKEN
import gdsc.plantory.util.TEST_PLANT_ID_HAS_HISTORY
import gdsc.plantory.util.TEST_PLANT_ID_NO_HISTORY
import gdsc.plantory.util.TEST_PLANT_INFO_ID
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.LocalDate
import java.time.YearMonth

@DisplayName("인수 : CompanionPlant")
class CompanionPlantAcceptanceTest : AcceptanceTest() {

    @Test
    fun `반려식물 등록`() {
        // given
        val 반려_식물_정보 = generateCompanionPlantCreateRequest(TEST_PLANT_INFO_ID)

        // when
        val 식물_등록_요청_응답 = 반려_식물_등록_요청(반려_식물_정보, TEST_MEMBER_TOKEN)

        // then
        응답_확인(식물_등록_요청_응답, HttpStatus.OK)
    }

    @Test
    fun `반려식물 물주기 히스토리 등록`() {
        // given
        val 물줌_기록 = PlantHistoryRequest(TEST_PLANT_ID_NO_HISTORY, "WATER_CHANGE")

        // when
        val 식물_히스토리_생성_응답 = 식물_히스토리_생성_요청(물줌_기록, TEST_MEMBER_TOKEN)

        // then
        응답_확인(식물_히스토리_생성_응답, HttpStatus.OK)
    }

    @Test
    fun `반려식물 조회`() {
        // when
        val 식물_조회_요청_응답 = 식물_조회_요청(TEST_MEMBER_TOKEN)

        // then
        식물_조회_응답_확인(식물_조회_요청_응답)
    }

    @Test
    fun `반려식물 데일리 기록 등록`() {
        // given
        val 데일리_기록_정보 = generatePlantRecordCreateRequest(TEST_PLANT_ID_NO_HISTORY)

        // when
        val 데일리_기록_등록_요청_응답 = 데일리_기록_등록_요청(데일리_기록_정보, TEST_MEMBER_TOKEN)

        // then
        응답_확인(데일리_기록_등록_요청_응답, HttpStatus.OK)
    }

    /**
     * given: 오늘 날짜에 데일리 기록을 작성한다
     * when: 오늘 날짜에 데일리 기록을 한번더 작성한다
     * then: Conflict 상태코드를 응답한다
     */
    @Test
    fun `반려식물 데일리 기록 중복 등록`() {
        // given
        데일리_기록_등록_요청(generatePlantRecordCreateRequest(TEST_PLANT_ID_NO_HISTORY), TEST_MEMBER_TOKEN)

        // when
        val 데일리_기록_등록_요청_응답 =
            데일리_기록_등록_요청(generatePlantRecordCreateRequest(TEST_PLANT_ID_NO_HISTORY), TEST_MEMBER_TOKEN)

        // then
        응답_확인(데일리_기록_등록_요청_응답, HttpStatus.CONFLICT)
    }

    @Test
    fun `반려식물 데일리 기록 조회`() {
        // given
        val 데일리_기록_조회_정보 = PlantRecordLookupRequest(TEST_PLANT_ID_HAS_HISTORY, LocalDate.now())

        // when
        val 데일리_기록_조회_요청_응답 = 데일리_기록_조회_요청(데일리_기록_조회_정보, TEST_MEMBER_TOKEN)

        // then
        데일리_기록_조회_응답_확인(데일리_기록_조회_요청_응답)
    }

    @Test
    fun `반려식물 히스토리 조회`() {
        // given
        val 히스토리_조회_정보 = PlantHistoriesLookupRequest(TEST_PLANT_ID_HAS_HISTORY, YearMonth.parse("2024-01"))

        // when
        val 히스토리_조회_요청_응답 = 히스토리_조회_요청(히스토리_조회_정보, TEST_MEMBER_TOKEN)

        // then
        히스토리_조회_응답_확인(히스토리_조회_요청_응답)
    }
}
