package gdsc.plantory.plantInformation.presentation

import gdsc.plantory.plantInformation.presentation.dto.PlantInformationsLookupResponse
import gdsc.plantory.plantInformation.service.PlantInformationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Plant Information Command", description = "식물 정보 조회")
@RestController
@RequestMapping("/api/v1/plantInformations")
class PlantInformationQueryApi(
    private val plantInformationService: PlantInformationService
) {
    @Operation(summary = "식물 정보 조회", description = "등록된 모든 식물 정보를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    fun lookupAllPlantInformations(): ResponseEntity<PlantInformationsLookupResponse> {
        val plantInformations = plantInformationService.lookupAllPlantInformations()
        return ResponseEntity.ok().body(plantInformations)
    }
}