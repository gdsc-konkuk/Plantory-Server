package gdsc.plantory.fixture

import gdsc.plantory.member.domain.Member

const val 테스터_디바이스_토큰 = "tester-token"

object MemberFixture {

    fun generateMember(deviceToken: String = "device-token"): Member {
        return Member(deviceToken = deviceToken)
    }
}