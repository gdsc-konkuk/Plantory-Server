package gdsc.plantory.plantInformation.domain

import NotFoundException
import org.springframework.data.jpa.repository.JpaRepository
import kotlin.jvm.optionals.getOrNull

fun PlantInformationRepository.findByIdOrThrow(id: Long): PlantInformation {
    return findById(id).getOrNull() ?: throw NotFoundException("식물 정보가 없어요")
}

interface PlantInformationRepository : JpaRepository<PlantInformation, Long> {
}
