package gdsc.plantory.plant.presentation.dto

import java.time.YearMonth

data class HistoriesLookupRequest(
    val companionPlantId: Long,
    val targetMonth: YearMonth
)
