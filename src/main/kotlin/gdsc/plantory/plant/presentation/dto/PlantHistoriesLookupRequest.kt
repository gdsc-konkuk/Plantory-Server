package gdsc.plantory.plant.presentation.dto

import java.time.YearMonth

data class PlantHistoriesLookupRequest(
    val companionPlantId: Long,
    val targetMonth: YearMonth
)
