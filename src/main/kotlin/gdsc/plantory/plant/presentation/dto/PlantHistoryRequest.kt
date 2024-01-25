package gdsc.plantory.plant.presentation.dto

import jakarta.validation.constraints.NotBlank

data class PlantHistoryRequest(
    @NotBlank val historyType: String,
)
