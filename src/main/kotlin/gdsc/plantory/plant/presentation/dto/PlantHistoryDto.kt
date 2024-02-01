package gdsc.plantory.plant.presentation.dto

import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plant.domain.PlantHistory
import java.time.LocalDate

data class PlantHistoryDto(
    val id: Long,
    val type: HistoryType,
    val date: LocalDate,
) {
    companion object {
        fun from(history: PlantHistory): PlantHistoryDto = PlantHistoryDto(
            id = history.getId,
            type = history.getType,
            date = history.getDate,
        )
    }
}
