package gdsc.plantory.plant.presentation.dto

import gdsc.plantory.plant.domain.CompanionPlant
import java.time.LocalDate

data class CompanionPlantDto(
    val id: Long,
    val imageUrl: String,
    val nickname: String,
    val shortDescription: String,
    val birthDate: LocalDate,
    val name: String,
) {
    companion object {
        fun from(plant: CompanionPlant): CompanionPlantDto = CompanionPlantDto(
            id = plant.getId,
            imageUrl = plant.getImageUrl,
            nickname = plant.getNickName,
            shortDescription = plant.getSortDescription,
            birthDate = plant.getBirthDate,
            name = plant.getName,
        )
    }
}
