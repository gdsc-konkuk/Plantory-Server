package gdsc.plantory.acceptance

import gdsc.plantory.acceptance.CommonStep.Companion.응답_확인
import gdsc.plantory.acceptance.MemberStep.Companion.회원_가입_요청
import gdsc.plantory.member.dto.MemberSignUpRequest
import gdsc.plantory.util.AcceptanceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus


@DisplayName("인수 : Member")
class MemberAcceptanceTest : AcceptanceTest() {

    @Test
    fun `회원 가입`() {
        // given
        val 회원_가입_정보 = MemberSignUpRequest("device-token")

        // when
        val 회원_가입_응답 = 회원_가입_요청(회원_가입_정보)

        // then
        응답_확인(회원_가입_응답, HttpStatus.OK)
    }

    /**
     * given: 회원 가입된 사용자 shine이 존재한다.
     * when: 동일한 device id인 shine으로 가입을 요청한다
     * then: Conflict 상태코드를 응답한다
     */
    @Test
    fun `중복 회원 가입`() {
        // given
        회원_가입_요청(MemberSignUpRequest("shine"))

        // when
        val 회원_가입_응답 = 회원_가입_요청(MemberSignUpRequest("shine"))

        // then
        응답_확인(회원_가입_응답, HttpStatus.CONFLICT)
    }
}
