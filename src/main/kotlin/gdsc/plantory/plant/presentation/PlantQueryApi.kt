package gdsc.plantory.plant.presentation

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.presentation.dto.CompanionPlantsLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupResponse
import gdsc.plantory.plant.service.PlantService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.YearMonth

@RestController
@RequestMapping("/api/v1/plants")
class PlantQueryApi(
    private val plantService: PlantService
) {

    @Operation(summary = "반려식물 조회", description = "사용자의 반려식물 목록을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping
    fun lookupAllCompanionPlantsOfMember(
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<CompanionPlantsLookupResponse> {
        val companionPlants = plantService.lookupAllCompanionPlantsOfMember(deviceToken)
        return ResponseEntity.ok().body(companionPlants)
    }

    @Operation(summary = "반려식물 히스토리(데일리 기록, 물줌, 분갈이) 조회", description = "해당 달의 반려식물 히스토리를 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{companionPlantId}/histories")
    fun lookupAllPlantHistoriesOfMonth(
        @Parameter(description = "조회할 반려식물 ID") @PathVariable companionPlantId: Long,
        @Parameter(description = "조회 기간(달)") @RequestParam targetMonth: YearMonth,
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<PlantHistoriesLookupResponse> {
        val request = PlantHistoriesLookupRequest(companionPlantId, targetMonth)
        val histories = plantService.lookupAllPlantHistoriesOfMonth(request, deviceToken)
        return ResponseEntity.ok().body(histories)
    }

    @Operation(summary = "반려식물 데일리 기록 조회", description = "데일리 기록(이미지, 일지 등)을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/{companionPlantId}/records")
    fun lookupPlantRecordOfDate(
        @Parameter(description = "데일리 기록의 반려식물") @PathVariable companionPlantId: Long,
        @Parameter(description = "데일리 기록의 날짜") @RequestParam recordDate: LocalDate,
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<PlantRecordLookupResponse> {
        val request = PlantRecordLookupRequest(companionPlantId, recordDate)
        val plantRecord = plantService.lookupPlantRecordOfDate(request, deviceToken)
        return ResponseEntity.ok().body(plantRecord)
    }
}
