package gdsc.plantory.plantInformation.domain

import gdsc.plantory.fixture.PlantInformationFixture.generatePlantInformation
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@DisplayName("도메인 : PlantInformation")
class PlantInformationTest {

    @Test
    fun `식물정보 생성`() {
        assertThatCode {
            PlantInformation(
                "https://nongsaro.go.kr/cms_contents/301/14687_MF_ATTACH_01.jpg",
                "Cactaceae", "Cactaceae; Juss.",
                "15 ~ 20", "5",
                5, 5, 4, 3,
                "earthy", "caution", "slow", "40%",
                "veranda",
            )
        }.doesNotThrowAnyException()
    }

    @ParameterizedTest
    @CsvSource(value = ["'',Cactaceae; Juss.", "Cactaceae,''", "'',''"])
    fun `Species가 공백이라면 예외를 반환`(species: String, familyName: String) {
        assertThrows<IllegalArgumentException> {
            generatePlantInformation(species = species, familyName = familyName)
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["'',5", "15 ~ 20,''", "'',''"])
    fun `Temperature가 공백이라면 예외를 반환`(requireTemp: String, minimumTemp: String) {
        assertThrows<IllegalArgumentException> {
            generatePlantInformation(requireTemp = requireTemp, minimumTemp = minimumTemp)
        }
    }

    @ParameterizedTest
    @CsvSource(value = ["-1,2,3,2", "3,0,3,2", "3,2,3,0", "3,-21,3,2"])
    fun `WaterCycle이 양의 정수가 아니라면 예외를 반환`(
        waterCycleSpring: Int, waterCycleSummer: Int, waterCycleAutumn: Int, waterCycleWinter: Int
    ) {
        assertThrows<IllegalArgumentException> {
            generatePlantInformation(
                waterCycleSpring = waterCycleSpring,
                waterCycleSummer = waterCycleSummer,
                waterCycleAutumn = waterCycleAutumn,
                waterCycleWinter = waterCycleWinter
            )
        }
    }
}
