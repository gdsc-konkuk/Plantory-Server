package gdsc.plantory.util

import gdsc.plantory.member.domain.Member
import gdsc.plantory.member.domain.MemberRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

private const val DEFAULT_FOLDER_NAME = "default"

@Component
class DatabaseLoader(
    private val memberRepository: MemberRepository,
) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this.javaClass)!!
    }

    fun loadData() {
        log.info("[call DataLoader]")

        val newMember = memberRepository.save(Member("device_id"))

        log.info("[init complete DataLoader]")
    }
}
