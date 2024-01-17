package gdsc.plantory.plant.presentation.dto

import gdsc.plantory.plant.domain.CompanionPlant

data class CompanionPlantsLookupResponse(
    val companionPlants: List<CompanionPlantDto>
) {
    companion object {
        fun from(companionPlants: List<CompanionPlant>): CompanionPlantsLookupResponse =
            CompanionPlantsLookupResponse(
                companionPlants
                    .stream()
                    .map { CompanionPlantDto.from(it) }
                    .toList()
            )
    }
}
