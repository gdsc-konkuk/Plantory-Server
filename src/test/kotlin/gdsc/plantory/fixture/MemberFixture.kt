package gdsc.plantory.fixture

import gdsc.plantory.member.domain.Member

const val 테스터_ID = 1L
const val 테스터_디바이스_토큰 = "device-token"

object MemberFixture {

    fun generateTestMember(): Member {
        return Member(
            deviceToken = 테스터_디바이스_토큰,
            id = 테스터_ID
        )
    }
}