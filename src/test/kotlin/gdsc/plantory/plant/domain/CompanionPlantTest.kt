package gdsc.plantory.plant.domain

import ConflictException
import gdsc.plantory.fixture.CompanionPlantFixture.generateCompanionPlant
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate

@DisplayName("도메인 : CompanionPlant")
class CompanionPlantTest {

    @Test
    fun `반려식물 생성`() {
        // given
        val waterCycle = 7L
        val lastWaterDate = LocalDate.now()
        val nextWaterDate = lastWaterDate.plusDays(waterCycle)

        // when, then
        assertThatCode {
            CompanionPlant(
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
                "나의 아기 선인장", "shine", nextWaterDate, lastWaterDate, waterCycle.toInt()
            )
        }
            .doesNotThrowAnyException()
    }

    @Test
    fun `반려식물에게 레코드 타입의 히스토리를 직접 저장하려는 경우 예외 발생`() {
        // given
        val companionPlant = generateCompanionPlant()

        // when, then
        assertThatThrownBy {
            companionPlant.saveHistory(HistoryType.RECORDING, LocalDate.now())
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("데일리 기록은 히스토리 타입을 직접 추가할 수 없습니다.")
    }

    @Test
    fun `물 준 주기가 맞지 않으면 예외 발생`() {
        // given
        val waterCycle = 7L
        val lastWaterDate = LocalDate.now()
        val nextWaterDate = lastWaterDate.minusDays(1)

        // when, then
        assertThatThrownBy {
            generateCompanionPlant(
                nextWaterDate = nextWaterDate,
                lastWaterDate = lastWaterDate,
                waterCycle = waterCycle.toInt()
            )
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("마지막으로 물 준 날짜는 다음에 물 줄 날짜보다 빠를 수 없습니다.")
    }

    @Test
    fun `데일리 기록 작성`() {
        // given
        val companionPlant = generateCompanionPlant()

        // when, then
        assertThatCode {
            companionPlant.saveRecord(
                "test_comment",
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg"
            )
        }
            .doesNotThrowAnyException()
    }

    @Test
    fun `데일리 기록을 이미 등록한 날짜에 중복 등록하면 예외가 발생`() {
        // given
        val companionPlant = generateCompanionPlant()
        companionPlant.saveRecord("오늘의 기록!")

        // when, then
        assertThatThrownBy {
            companionPlant.saveRecord("이것 역시 오늘의 기록!")
        }
            .isInstanceOf(ConflictException::class.java)
    }

    @Test
    fun `히스토리 생성`() {
        // given
        val companionPlant = generateCompanionPlant()

        // when
        companionPlant.saveHistory(HistoryType.WATER_CHANGE, LocalDate.now())
        companionPlant.saveHistory(HistoryType.POT_CHANGE, LocalDate.now())

        // then
        assertThat(companionPlant.historySize()).isEqualTo(2)
    }

    @Test
    fun `식물 별칭이 너무 길 경우 예외가 발생`() {
        // given
        val tooLongNickName = "17자리 너무 긴 별칭!!!!!"

        // when, then
        assertThatThrownBy {
            generateCompanionPlant(nickname = tooLongNickName)
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("\"nickName\"은 16자를 초과할 수 없습니다.")
    }

    @Test
    fun `소개 문구가 너무 길 경우 예외가 발생`() {
        // given
        val tooLongDescription = "17자리 너무 긴 소개 문구!!"

        // when, then
        assertThatThrownBy {
            generateCompanionPlant(shortDescription = tooLongDescription)
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("\"shortDescription\"은 16자를 초과할 수 없습니다.")
    }

    @ParameterizedTest
    @CsvSource(value = ["2023,1,1,1", "2023,1,7,7", "2023,2,1,32"])
    fun `반려식물과 함께한 일수 계산`(year: Int, month: Int, day: Int, daySince: Int) {
        // given
        val companionPlant = generateCompanionPlant(birthDate = LocalDate.of(2023, 1, 1))

        // when
        val result: Int = companionPlant.calculateDaySince(LocalDate.of(year, month, day))

        // then
        assertThat(result).isEqualTo(daySince)
    }

    @Test
    fun `함께한날 계산시 생일 이전의 날을 입력하면 예외가 발생`() {
        // given
        val companionPlant = generateCompanionPlant(birthDate = LocalDate.of(2023, 1, 1))

        // when, then
        assertThatThrownBy {
            companionPlant.calculateDaySince(LocalDate.of(1995, 5, 30))
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("함께한 날은 음수가 될 수 없습니다.")
    }

    @Test
    fun `물을 주면 다음에 물을 주어야 할 날짜와 마지막으로 물 준 날짜 변경`() {
        // given
        val companionPlant: CompanionPlant = generateCompanionPlant(
            lastWaterDate = LocalDate.of(1995, 5, 25),
            nextWaterDate = LocalDate.of(1995, 5, 30),
            waterCycle = 5
        )
        val currentWaterDate = LocalDate.now()
        val nextWaterDate = currentWaterDate.plusDays(5)

        // when
        companionPlant.saveHistory(HistoryType.WATER_CHANGE, currentWaterDate)

        assertAll(
            { assertThat(companionPlant.getNextWaterDate).isEqualTo(nextWaterDate) },
            { assertThat(companionPlant.getLastWaterDate).isEqualTo(currentWaterDate) }
        )
    }

    @Test
    fun `당일이 아닌 날에 물을 줄려 하는 경우 예외 발생`() {
        // given
        val companionPlant: CompanionPlant = generateCompanionPlant()
        val currentWaterDate = LocalDate.of(2024, 1, 10)

        // when, then
        assertThatThrownBy {
            companionPlant.saveHistory(HistoryType.WATER_CHANGE, currentWaterDate.minusDays(1))
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("물을 줄 날짜는 오늘 날짜여야 합니다.")
    }
}
