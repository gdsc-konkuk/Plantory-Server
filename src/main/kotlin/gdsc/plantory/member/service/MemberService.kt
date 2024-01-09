package gdsc.plantory.member.service

import ConflictException
import gdsc.plantory.member.domain.Member
import gdsc.plantory.member.domain.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
) {

    fun signUp(deviceId: String) {
        validateDuplicateMember(deviceId)
        registerMember(deviceId)
    }

    private fun validateDuplicateMember(deviceId: String) {
        memberRepository.findByDeviceToken(deviceId)
            ?.let { throw ConflictException() }
    }

    private fun registerMember(deviceId: String) {
        memberRepository.save(Member(deviceId))
    }
}
