package gdsc.plantory.plant.presentation

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.presentation.dto.CompanionPlantsLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoriesLookupResponse
import gdsc.plantory.plant.presentation.dto.PlantRecordDto
import gdsc.plantory.plant.presentation.dto.PlantRecordLookupRequest
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
    fun lookupAllPlantsOfMember(
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<CompanionPlantsLookupResponse> {
        val companionPlants = plantService.lookupAllPlantsOfMember(deviceToken)
        return ResponseEntity.ok().body(CompanionPlantsLookupResponse(companionPlants))
    }

    @GetMapping("/histories", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun lookupAllHistoriesOfMonth(
        @RequestBody request: PlantHistoriesLookupRequest,
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<PlantHistoriesLookupResponse> {
        val histories = plantService.lookupAllHistoriesOfMonth(request, deviceToken)
        return ResponseEntity.ok().body(PlantHistoriesLookupResponse(histories))
    }

    @GetMapping("/records", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun lookupPlantRecordOfDate(
        @RequestBody request: PlantRecordLookupRequest,
        @AccessDeviceToken deviceToken: String
    ): ResponseEntity<PlantRecordDto> {
        val plantRecord = plantService.lookupPlantRecordOfDate(request, deviceToken)
        return ResponseEntity.ok().body(plantRecord)
    }
}
