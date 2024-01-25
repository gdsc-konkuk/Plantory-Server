package gdsc.plantory.plant.presentation

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.presentation.dto.CompanionPlantsLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupResponse
import gdsc.plantory.plant.service.PlantService
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

    @GetMapping
    fun lookupAllCompanionPlantsOfMember(
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<CompanionPlantsLookupResponse> {
        val companionPlants = plantService.lookupAllCompanionPlantsOfMember(deviceToken)
        return ResponseEntity.ok().body(companionPlants)
    }

    @GetMapping("/{companionPlantId}/histories")
    fun lookupAllPlantHistoriesOfMonth(
        @PathVariable companionPlantId: Long,
        @RequestParam targetMonth: YearMonth,
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<PlantHistoriesLookupResponse> {
        val request = PlantHistoriesLookupRequest(companionPlantId, targetMonth)
        val histories = plantService.lookupAllPlantHistoriesOfMonth(request, deviceToken)
        return ResponseEntity.ok().body(histories)
    }

    @GetMapping("/{companionPlantId}/records")
    fun lookupPlantRecordOfDate(
        @PathVariable companionPlantId: Long,
        @RequestParam recordDate: LocalDate,
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<PlantRecordLookupResponse> {
        val request = PlantRecordLookupRequest(companionPlantId, recordDate)
        val plantRecord = plantService.lookupPlantRecordOfDate(request, deviceToken)
        return ResponseEntity.ok().body(plantRecord)
    }
}
