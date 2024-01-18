package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.CommonStep.Companion.응답_확인
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_등록_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_조회_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.데일리_기록_등록_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.데일리_기록_조회_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.데일리_기록_조회_응답_확인
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.반려_식물_삭제_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_히스토리_생성_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.식물_조회_응답_확인
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.히스토리_조회_요청
import gdsc.plantory.acceptance.CompanionPlantStep.Companion.히스토리_조회_응답_확인
import gdsc.plantory.fixture.기록없는_테스트식물_ID
import gdsc.plantory.fixture.기록있는_테스트식물_ID
import gdsc.plantory.fixture.테스터_디바이스_토큰
import gdsc.plantory.fixture.테스트_식물정보_ID
import gdsc.plantory.fixture.CompanionPlantFixture.generateCompanionPlantCreateRequest
import gdsc.plantory.fixture.CompanionPlantFixture.generatePlantRecordCreateRequest
import gdsc.plantory.plant.presentation.dto.CompanionPlantDeleteRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoryRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupRequest
import gdsc.plantory.util.AcceptanceTest
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
        val 반려_식물_정보 = generateCompanionPlantCreateRequest(테스트_식물정보_ID)

        // when
        val 식물_등록_요청_응답 = 반려_식물_등록_요청(반려_식물_정보, 테스터_디바이스_토큰)

        // then
        응답_확인(식물_등록_요청_응답, HttpStatus.OK)
    }

    @Test
    fun `반려식물 삭제`() {
        // given
        val 반려_식물_정보 = CompanionPlantDeleteRequest(기록있는_테스트식물_ID)

        // when
        val 식물_삭제_요청_응답 = 반려_식물_삭제_요청(반려_식물_정보, 테스터_디바이스_토큰)

        // then
        응답_확인(식물_삭제_요청_응답, HttpStatus.OK)
    }

    @Test
    fun `반려식물 물주기 히스토리 등록`() {
        // given
        val 물줌_기록 = PlantHistoryRequest(기록없는_테스트식물_ID, "WATER_CHANGE")

        // when
        val 식물_히스토리_생성_응답 = 식물_히스토리_생성_요청(물줌_기록, 테스터_디바이스_토큰)

        // then
        응답_확인(식물_히스토리_생성_응답, HttpStatus.OK)
    }

    @Test
    fun `반려식물 조회`() {
        // when
        val 식물_조회_요청_응답 = 식물_조회_요청(테스터_디바이스_토큰)

        // then
        식물_조회_응답_확인(식물_조회_요청_응답)
    }

    @Test
    fun `반려식물 데일리 기록 등록`() {
        // given
        val 데일리_기록_정보 = generatePlantRecordCreateRequest(기록없는_테스트식물_ID)

        // when
        val 데일리_기록_등록_요청_응답 = 데일리_기록_등록_요청(데일리_기록_정보, 테스터_디바이스_토큰)

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
        데일리_기록_등록_요청(
            generatePlantRecordCreateRequest(기록없는_테스트식물_ID), 테스터_디바이스_토큰
        )

        // when
        val 데일리_기록_등록_요청_응답 =
            데일리_기록_등록_요청(
                generatePlantRecordCreateRequest(기록없는_테스트식물_ID), 테스터_디바이스_토큰
            )

        // then
        응답_확인(데일리_기록_등록_요청_응답, HttpStatus.CONFLICT)
    }

    @Test
    fun `반려식물 데일리 기록 조회`() {
        // given
        val 데일리_기록_조회_정보 = PlantRecordLookupRequest(기록있는_테스트식물_ID, LocalDate.now())

        // when
        val 데일리_기록_조회_요청_응답 = 데일리_기록_조회_요청(데일리_기록_조회_정보, 테스터_디바이스_토큰)

        // then
        데일리_기록_조회_응답_확인(데일리_기록_조회_요청_응답)
    }

    @Test
    fun `반려식물 히스토리 조회`() {
        // given
        val 히스토리_조회_정보 = PlantHistoriesLookupRequest(기록있는_테스트식물_ID, YearMonth.parse("2024-01"))

        // when
        val 히스토리_조회_요청_응답 = 히스토리_조회_요청(히스토리_조회_정보, 테스터_디바이스_토큰)

        // then
        히스토리_조회_응답_확인(히스토리_조회_요청_응답)
    }
}
