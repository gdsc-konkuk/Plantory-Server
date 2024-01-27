package gdsc.plantory.plant.domain

import NotFoundException
import gdsc.plantory.plant.presentation.dto.CompanionPlantLookupDto
import gdsc.plantory.plant.presentation.dto.CompanionPlantWaterCycleDto
import gdsc.plantory.plant.presentation.dto.PlantRecordDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

fun CompanionPlantRepository.findByIdAndMemberIdOrThrow(id: Long, memberId: Long): CompanionPlant {
    return findByIdAndMemberId(id, memberId) ?: throw NotFoundException("식물 정보가 없어요")
}

interface CompanionPlantRepository : JpaRepository<CompanionPlant, Long> {

    fun findByIdAndMemberId(id: Long, memberId: Long): CompanionPlant?
    fun removeByIdAndMemberId(id: Long, memberId: Long)

    @Query(
        """
            SELECT new gdsc.plantory.plant.presentation.dto.PlantRecordDto(
                r.id,
                r.imageUrl._value,
                r.comment.content,
                cp.nickname._value
            )
            FROM CompanionPlant cp LEFT JOIN PlantRecord r
            ON cp.id = r.companionPlant.id
            WHERE cp.id = :companionPlantId
            AND cp.memberId = :memberId 
            AND DATE(r.createAt) = :recordDate
        """
    )
    fun findRecordByDate(companionPlantId: Long, memberId: Long, recordDate: LocalDate): PlantRecordDto

    @Query(
        """
            SELECT history.type
            FROM PlantHistory history
            WHERE 
                history.companionPlant.id = :companionPlantId
                AND DATE(history.createAt) = :recordDate
        """
    )
    fun findAllHistoryTypeByDate(companionPlantId: Long, recordDate: LocalDate): List<HistoryType>

    @Query(
        """
            SELECT new gdsc.plantory.plant.presentation.dto.CompanionPlantLookupDto(
                plant.id,
                plant.imageUrl._value,
                plant.nickname._value,
                plant.shortDescription._value,
                plant.birthDate,
                information.species.name
            )
            FROM PlantInformation information JOIN CompanionPlant plant
            ON information.id = plant.plantInformationId
            WHERE plant.memberId = :memberId
        """
    )
    fun findAllByMemberId(memberId: Long): List<CompanionPlantLookupDto>

    @Query(
        """
            SELECT new gdsc.plantory.plant.presentation.dto.CompanionPlantWaterCycleDto(
                member.deviceToken,
                plant.nickname._value
            )
            FROM Member member JOIN CompanionPlant plant
            ON member.id = plant.memberId
            WHERE plant.nextWaterDate = :date
        """
    )
    fun findAllByNextWaterDate(date: LocalDate): List<CompanionPlantWaterCycleDto>

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
}
