package gdsc.plantory.plant.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CompanionPlantRepository : JpaRepository<CompanionPlant, Long> {
    @Query(value = "SELECT plant FROM CompanionPlant plant WHERE plant.id = :memberId")
    fun findAllByMemberid(memberId: Long): MutableList<CompanionPlant>
}
