package gdsc.plantory.plantInformation.service

import gdsc.plantory.plantInformation.domain.PlantInformationRepository
import gdsc.plantory.plantInformation.presentation.dto.PlantInformationsLookupResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PlantInformationService(
    private val plantInformationRepository: PlantInformationRepository,
) {
    @Transactional(readOnly = true)
    fun lookupAllPlantInformations(): PlantInformationsLookupResponse {
        val findPlantInformations = plantInformationRepository.findAllSpeciesInformations()

        return PlantInformationsLookupResponse(findPlantInformations)
    }
}