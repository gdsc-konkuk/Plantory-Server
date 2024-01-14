package gdsc.plantory.plant.presentation.dto

import java.time.LocalDate

data class CompanionPlantResponse(
    val id: Long,
    val imageUrl: String,
    val nickname: String,
    val shortDescription: String,
    val birthDate: LocalDate,
)