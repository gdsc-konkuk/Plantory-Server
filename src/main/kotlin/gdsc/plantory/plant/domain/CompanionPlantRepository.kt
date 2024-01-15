package gdsc.plantory.plant.domain

import NotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

fun CompanionPlantRepository.findByIdAndMemberIdOrThrow(id: Long, memberId: Long): CompanionPlant {
    return findByIdAndMemberId(id, memberId) ?: throw NotFoundException("식물 정보가 없어요")
}

fun CompanionPlantRepository.findRecordByIdAndMemberIdAndDateOrThrow(
    id: Long,
    memberId: Long,
    date: LocalDate
): PlantRecord {
    val records = findAllRecordByIdAndMemberIdAndDate(id, memberId, date)

    if (records.isEmpty()) {
        throw NotFoundException("데일리 기록이 없어요")
    }

    return records[0]
}

interface CompanionPlantRepository : JpaRepository<CompanionPlant, Long> {

//    XXX Spring Data JPA의 projection이 kotlin에서 잘 동작하지 않음
//    @Query(
//        """
//        SELECT new gdsc.plantory.plant.common.dto.CompanionPlantDto(
//            c.id, c.imageUrl._value, c.nickname._value, c.shortDescription._value, c.birthDate)
//        FROM CompanionPlant c
//        WHERE c.memberId = :memberId
//    """
//    )
//    fun findAllPlantDtoByMemberId(memberId: Long): List<CompanionPlantDto>

    fun findAllByMemberId(memberId: Long): List<CompanionPlant>
    fun findByIdAndMemberId(id: Long, memberId: Long): CompanionPlant?

    // XXX Failed to convert from type [java.lang.Object[]] to type [gdsc.plantory.plant.domain.PlantRecord] for value [{...}]
//    @Query(
//        """
//        SELECT *
//        FROM plant_record r INNER JOIN companion_plant p ON r.companion_plant_id = p.id
//        WHERE p.id = :id AND p.member_id = :memberId AND DATE(r.created_at) = :date
//        """,
//        nativeQuery = true
//    )
    @Query(
        """
            SELECT record FROM PlantRecord record
            WHERE 
                record.companionPlant.id = :id 
                AND record.companionPlant.memberId = :memberId 
                AND DATE(record.createAt) = :date
        """
    )
    fun findAllRecordByIdAndMemberIdAndDate(
        id: Long,
        memberId: Long,
        date: LocalDate
    ): List<PlantRecord>
}
