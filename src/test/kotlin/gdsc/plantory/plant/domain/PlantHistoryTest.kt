package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.time.LocalDate

@DisplayName("도메인 : PlantHistory")
class PlantHistoryTest {

    @Test
    fun `히스토리 생성`() {
        assertThatCode { PlantHistory(HistoryType.WATER_CHANGE, LocalDate.now()) }.doesNotThrowAnyException()
    }
}
