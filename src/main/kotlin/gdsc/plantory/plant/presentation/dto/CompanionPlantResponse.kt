package gdsc.plantory.plant.presentation.dto

import gdsc.plantory.plant.domain.CompanionPlant

class CompanionPlantResponse(
    private val entity: CompanionPlant
) {
    val id = entity.getId
    val imageUrl = entity.getImageUrl
    val nickname = entity.getNickName
    val shortDescription = entity.getSortDescription
    val birthDate = entity.getBirthDate
}