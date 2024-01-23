package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@DisplayName("도메인 : HistoryType")
class HistoryTypeTest {

    @ParameterizedTest
    @MethodSource("historyTypeInputProvider")
    fun `문자열로부터 Enum으로 변환`(input: String, historyType: HistoryType) {
        // when
        val result = HistoryType.byNameIgnoreCaseOrNull(input)

        // then
        assertThat(result).isEqualTo(historyType)
    }

    companion object {
        @JvmStatic
        private fun historyTypeInputProvider(): Stream<Arguments?>? {
            return Stream.of<Arguments?>(
                Arguments.of("WATER_CHANGE", HistoryType.WATER_CHANGE),
                Arguments.of("water_change", HistoryType.WATER_CHANGE),
                Arguments.of("POT_CHANGE", HistoryType.POT_CHANGE),
                Arguments.of("pot_change", HistoryType.POT_CHANGE),
                Arguments.of("RECORDING", HistoryType.RECORDING),
                Arguments.of("recording", HistoryType.RECORDING)
            )
        }
    }

}
