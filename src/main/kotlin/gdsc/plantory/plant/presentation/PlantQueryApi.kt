package gdsc.plantory.plant.presentation

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.presentation.dto.CompanionPlantsLookupResponse
import gdsc.plantory.plant.service.PlantService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
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
}
