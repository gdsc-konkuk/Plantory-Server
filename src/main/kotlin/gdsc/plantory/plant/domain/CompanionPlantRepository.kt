package gdsc.plantory.plant.domain

import NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

fun CompanionPlantRepository.findByIdAndMemberIdOrThrow(id: Long, memberId: Long): CompanionPlant {
    return findByIdAndMemberId(id, memberId) ?: throw NotFoundException("식물 정보가 없어요")
}

interface CompanionPlantRepository : JpaRepository<CompanionPlant, Long> {
    fun findAllByMemberId(memberId: Long): List<CompanionPlant>
    fun findByIdAndMemberId(id: Long, memberId: Long): CompanionPlant?
}
