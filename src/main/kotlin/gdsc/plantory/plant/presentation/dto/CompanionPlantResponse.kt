package gdsc.plantory.plant.presentation.dto

import java.time.LocalDate

class CompanionPlantResponse(
    val id: Long,
    val imageUrl: String,
    val nickname: String,
    val shortDescription: String,
    val memberId: Long,
    val plantInformationId: Long,
    val nextWaterDate: LocalDate,
    var lastWaterDate: LocalDate,
    var waterCycle: Int,
    val birthDate: LocalDate,
)