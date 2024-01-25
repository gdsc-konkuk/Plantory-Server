package gdsc.plantory.plantInformation.presentation.dto

data class PlantInformationDto(
    val id: Long,
    val imageUrl: String,
    val species: String,
    val familyName: String
)