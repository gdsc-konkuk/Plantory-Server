package gdsc.plantory.plant.domain

import org.springframework.data.jpa.repository.JpaRepository

interface CompanionPlantRepository : JpaRepository<CompanionPlant, Long> {
    fun findAllByMemberId(memberId: Long): List<CompanionPlant>
}
