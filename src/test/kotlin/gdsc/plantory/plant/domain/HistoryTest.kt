package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("도메인 : History")
class HistoryTest {

    @Test
    fun `히스토리_생성_테스트`() {
        Assertions.assertThatCode { History(HistoryType.WATER_CHANGE) }.doesNotThrowAnyException()
    }
}
