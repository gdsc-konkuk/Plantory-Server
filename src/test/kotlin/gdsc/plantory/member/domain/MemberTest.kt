package gdsc.plantory.member.domain

import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("도메인 : Member")
class MemberTest {

    @Test
    fun `멤버 생성`() {
        assertThatCode { Member("device_token") }.doesNotThrowAnyException()
    }
}
