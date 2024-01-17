package gdsc.plantory.plant.presentation.dto

import gdsc.plantory.plant.domain.PlantHistory

data class PlantHistoriesLookupResponse(
    val histories: List<PlantHistoryDto>
) {
    companion object {
        fun from(histories: List<PlantHistory>): PlantHistoriesLookupResponse =
            PlantHistoriesLookupResponse(
                histories
                    .stream()
                    .map { PlantHistoryDto.from(it) }
                    .toList()
            )
    }
}
