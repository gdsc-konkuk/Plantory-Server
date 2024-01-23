package gdsc.plantory.plant.presentation.dto

data class PlantRecordCreateRequest(
    val companionPlantId: Long,
    val comment: String,
)