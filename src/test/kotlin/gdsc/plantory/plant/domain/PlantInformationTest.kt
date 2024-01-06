package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("도메인 : PlantInformation")
class PlantInformationTest {

    @Test
    fun `식물정보 생성 테스트`() {
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
}