package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDate


@DisplayName("도메인 : CompanionPlant")
class CompanionPlantTest {

    @Test
    fun `반려식물 생성 테스트`() {
        assertThatCode {
            CompanionPlant(
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
                "나의 아기 선인장", "shine", LocalDate.now(), LocalDate.now().plusDays(7), 7
            )
        }
            .doesNotThrowAnyException()
    }

    @Test
    fun `기록 작성 테스트`() {
        // given
        val companionPlant = CompanionPlant(
            "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
            "나의 아기 선인장", "shine", LocalDate.now(), LocalDate.now().plusDays(7), 7
        )

        // when
        companionPlant.saveRecord("test_comment", "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg")
        companionPlant.saveRecord("test_comment")

        // then
        assertThat(companionPlant.recordSize()).isEqualTo(2)
    }

    @Test
    fun `히스토리 생성 테스트`() {
        // given
        val companionPlant = CompanionPlant(
            "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
            "나의 아기 선인장", "shine", LocalDate.now(), LocalDate.now().plusDays(7), 7
        )

        // when
        companionPlant.saveHistory(HistoryType.WATER_CHANGE, LocalDate.now())
        companionPlant.saveHistory(HistoryType.POT_CHANGE, LocalDate.now())

        // then
        assertThat(companionPlant.historySize()).isEqualTo(2)
    }

    @Test
    fun `식물 별칭 테스트`() {
        // given
        val nickName = "16자리 짧은 소개 문구!!!!"

        // when, then
        assertThatThrownBy {
            CompanionPlant(
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
                "shortDescription", nickName, LocalDate.now(), LocalDate.now().plusDays(7),
                7, LocalDate.of(2023, 1, 1)
            )
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("\"nickName\"은 16자를 초과할 수 없습니다.")
    }

    @Test
    fun `짧은 소개 문구 테스트`() {
        // given
        val shortDescription = "16자리 짧은 소개 문구!!!!"

        // when, then
        assertThatThrownBy {
            CompanionPlant(
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
                shortDescription, "shine", LocalDate.now(), LocalDate.now().plusDays(7),
                7, LocalDate.of(2023, 1, 1)
            )
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("\"shortDescription\"은 16자를 초과할 수 없습니다.")
    }

    @ParameterizedTest
    @CsvSource(value = ["2023,1,1,1", "2023,1,7,7", "2023,2,1,32"])
    fun `반려식물과 함께한 일수 계산`(year: Int, month: Int, day: Int, daySince: Int) {
        // given
        val companionPlant = CompanionPlant(
            "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
            "나의 아기 선인장", "shine", LocalDate.now(), LocalDate.now().plusDays(7),
            7, LocalDate.of(2023, 1, 1)
        )

        // when
        val result: Int = companionPlant.calculateDaySince(LocalDate.of(year, month, day))

        // then
        assertThat(result).isEqualTo(daySince)
    }

    @Test
    fun `함께한날 계산시 생일 이전의 날을 입력하면 예외가 발생`() {
        // given
        val companionPlant = CompanionPlant(
            "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
            "나의 아기 선인장", "shine", LocalDate.now(), LocalDate.now().plusDays(7),
            7, LocalDate.of(2023, 1, 1)
        )

        // when, then
        assertThatThrownBy {
            companionPlant.calculateDaySince(LocalDate.of(1995, 5, 30))
        }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("함께한 날은 음수가 될 수 없습니다.")
    }
}
