package gdsc.plantory.fixture

import gdsc.plantory.member.domain.Member

private var _테스터_ID = 0L
val 테스터_ID: Long
    get() = _테스터_ID

const val 테스터_디바이스_토큰 = "device-token"

object MemberFixture {

    fun generateTestMember(id: Long): Member {
        _테스터_ID = id

        return Member(
            deviceToken = 테스터_디바이스_토큰,
            id = id
        )
    }
}