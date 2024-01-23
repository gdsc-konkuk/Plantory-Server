package gdsc.plantory.plant.presentation.dto

import java.time.LocalDate

data class PlantRecordLookupRequest(
    val companionPlantId: Long,
    val recordDate: LocalDate
)