package gdsc.plantory.plant.domain

import NotFoundException
import org.springframework.data.jpa.repository.JpaRepository

fun CompanionPlantRepository.findByIdAndMemberIdOrThrow(id: Long, memberId: Long): CompanionPlant {
    return findByIdAndMemberId(id, memberId) ?: throw NotFoundException("식물 정보가 없어요")
}

interface CompanionPlantRepository : JpaRepository<CompanionPlant, Long> {

    //    @Query("""
//        SELECT new gdsc.plantory.plant.common.dto.CompanionPlantDto(
//            c.id, c.imageUrl._value, c.nickname._value, c.shortDescription._value, c.birthDate)
//        FROM CompanionPlant c
//        WHERE c.memberId = :memberId
//    """)
//    fun findAllPlantDtoByMemberId(memberId: Long): List<CompanionPlantDto>
    fun findAllByMemberId(memberId: Long): List<CompanionPlant>
    fun findByIdAndMemberId(id: Long, memberId: Long): CompanionPlant?
}
