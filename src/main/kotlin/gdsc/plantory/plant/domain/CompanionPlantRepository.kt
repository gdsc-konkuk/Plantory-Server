package gdsc.plantory.plant.domain

import NotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

fun CompanionPlantRepository.findByIdAndMemberIdOrThrow(id: Long, memberId: Long): CompanionPlant {
    return findByIdAndMemberId(id, memberId) ?: throw NotFoundException("식물 정보가 없어요")
}

fun CompanionPlantRepository.findRecordByDateOrThrow(id: Long, memberId: Long, date: LocalDate): PlantRecord {
    return findRecordByDate(id, memberId, date) ?: throw NotFoundException("데일리 기록이 없어요")
}

interface CompanionPlantRepository : JpaRepository<CompanionPlant, Long> {

    fun findAllByMemberId(memberId: Long): List<CompanionPlant>
    fun findByIdAndMemberId(id: Long, memberId: Long): CompanionPlant?
    fun removeByIdAndMemberId(id: Long, memberId: Long)

    @Query(
        """
            SELECT history FROM PlantHistory history
            WHERE 
                history.companionPlant.id = :id 
                AND history.companionPlant.memberId = :memberId
                AND YEAR(history.date) = :year 
                AND MONTH(history.date) = :month
        """
    )
    fun findAllHistoriesByMonth(id: Long, memberId: Long, year: Int, month: Int): List<PlantHistory>

    @Query(
        """
            SELECT record FROM PlantRecord record
            WHERE 
                record.companionPlant.id = :id 
                AND record.companionPlant.memberId = :memberId 
                AND DATE(record.createAt) = :date
        """
    )
    fun findRecordByDate(id: Long, memberId: Long, date: LocalDate): PlantRecord?
}
