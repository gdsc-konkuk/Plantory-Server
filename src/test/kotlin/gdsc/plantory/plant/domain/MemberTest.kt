package gdsc.plantory.plant.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

@DisplayName("도메인 : Member")
class MemberTest {

    @Test
    fun `멤버 생성 테스트`() {
        assertThatCode { Member("device_token") }.doesNotThrowAnyException()
    }

    @Test
    fun `"device_token"이 공백이라면 예외를 반환`() {
        assertThrows<IllegalArgumentException> { Member("") }
    }

    @Test
    fun `"device_token" 길이 제한 초과하면 예외를 반환`() {
        // length: 301
        val longDeviceToken = "htfdsfdswwwgggggle.casdfdsrchq353EC%;;u;8%EB%8dsfajaslkfjasdkfjkldsafjlsadkjf" +
                "lksajfldkadsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjlasjf;" +
                "ljlfsaddsfajaslkfjasdkfjkldsafjlsadkjflksajfldkajlafkj;lkdjalkfjads;jfalksdfjlasjf;ljlf" +
                "sadjlafkj;lkdjalkfjads;jfalksdfjlasjf;ljdsfajaslkfj"

        assertThrows<IllegalArgumentException> { Member(longDeviceToken) }
    }
}