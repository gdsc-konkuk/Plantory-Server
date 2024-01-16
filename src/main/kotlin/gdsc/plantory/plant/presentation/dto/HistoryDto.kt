package gdsc.plantory.plant.presentation.dto

import gdsc.plantory.plant.domain.History
import gdsc.plantory.plant.domain.HistoryType
import java.time.LocalDate

data class HistoryDto(
    val id: Long,
    val type: HistoryType,
    val date: LocalDate,
) {
    companion object {
        fun from(history: History): HistoryDto = HistoryDto(
            id = history.getId,
            type = history.getType,
            date = history.getDate,
        )
    }
}
