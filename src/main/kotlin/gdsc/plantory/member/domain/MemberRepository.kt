package gdsc.plantory.member.domain

import NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

fun MemberRepository.findByDeviceTokenOrThrow(deviceId: String): Member {
    return findByDeviceToken(deviceId) ?: throw NotFoundException("회원이 없어요")
}

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByDeviceToken(deviceToken: String): Member?
}
