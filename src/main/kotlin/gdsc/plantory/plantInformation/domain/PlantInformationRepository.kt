package gdsc.plantory.plantInformation.domain

import NotFoundException
import gdsc.plantory.plantInformation.presentation.dto.PlantInformationDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import kotlin.jvm.optionals.getOrNull

fun PlantInformationRepository.findByIdOrThrow(id: Long): PlantInformation {
    return findById(id).getOrNull() ?: throw NotFoundException("식물 정보가 없어요")
}

interface PlantInformationRepository : JpaRepository<PlantInformation, Long> {
    @Query(
        """
            SELECT new gdsc.plantory.plantInformation.presentation.dto.PlantInformationDto(
                pi.id,
                pi.imageUrl._value,
                pi.species.name,
                pi.species.familyName
            )
            FROM PlantInformation pi
        """
    )
    fun findAllProjected(): List<PlantInformationDto>
}
