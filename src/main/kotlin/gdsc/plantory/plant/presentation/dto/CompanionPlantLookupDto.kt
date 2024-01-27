package gdsc.plantory.plant.presentation.dto

import java.time.LocalDate

class CompanionPlantLookupDto(
    val id: Long,
    val imageUrl: String,
    val nickname: String,
    val shortDescription: String,
    val birthDate: LocalDate,
    val name: String,
)
