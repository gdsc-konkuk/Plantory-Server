package gdsc.plantory.plant.presentation.dto

import gdsc.plantory.plant.domain.PlantRecord

data class PlantRecordDto(
    val id: Long,
    val imageUrl: String,
    val comment: String,
) {
    companion object {
        fun from(plant: PlantRecord): PlantRecordDto = PlantRecordDto(
            id = plant.getId,
            imageUrl = plant.getImageUrl,
            comment = plant.getComment,
        )
    }
}
