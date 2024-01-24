package gdsc.plantory.plant.presentation

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.presentation.dto.CompanionPlantsLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupResponse
import gdsc.plantory.plant.service.PlantService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

    @GetMapping("/histories", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun lookupAllPlantHistoriesOfMonth(
        @RequestBody request: PlantHistoriesLookupRequest,
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<PlantHistoriesLookupResponse> {
        val histories = plantService.lookupAllPlantHistoriesOfMonth(request, deviceToken)
        return ResponseEntity.ok().body(histories)
    }

    @GetMapping("/records", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun lookupPlantRecordOfDate(
        @RequestBody request: PlantRecordLookupRequest,
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<PlantRecordLookupResponse> {
        val plantRecord = plantService.lookupPlantRecordOfDate(request, deviceToken)
        return ResponseEntity.ok().body(plantRecord)
    }
}
