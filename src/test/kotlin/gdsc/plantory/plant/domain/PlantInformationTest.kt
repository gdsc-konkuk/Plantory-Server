package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("도메인 : PlantInformation")
class PlantInformationTest {

    @ParameterizedTest
    @ValueSource(strings = ["Cactus", "Chrysanthemum", "Rose"])
    fun `식물정보 생성 테스트`(name: String) {
        assertThatCode {
            PlantInformation(
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
                name
            )
        }.doesNotThrowAnyException()
    }

    @Test
    fun `"name"이 공백이라면 예외를 반환`() {
        assertThrows<IllegalArgumentException> {
            PlantInformation(
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
                ""
            )
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["3,3,2,2", "4,4,4,4", "5,5,1,1", "2,2,1,3"])
    fun `물주기를 포함한 식물정보 생성 테스트`(spring: String, summer: String, autumn: String, winter: String) {
        assertThatCode {
            PlantInformation(
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
                "Cactus", waterCycle = WaterCycle(spring, summer, autumn, winter)
            )
        }.doesNotThrowAnyException()
    }
}