package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

@DisplayName("도메인 : History")
class HistoryTest {

    @Test
    fun `히스토리 생성`() {
        assertThatCode { History(HistoryType.WATER_CHANGE, LocalDate.now()) }.doesNotThrowAnyException()
    }
}
