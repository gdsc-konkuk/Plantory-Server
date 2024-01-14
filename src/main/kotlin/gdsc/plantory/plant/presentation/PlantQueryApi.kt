package gdsc.plantory.plant.presentation

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.domain.CompanionPlant
import gdsc.plantory.plant.presentation.dto.CompanionPlantLookupRequest
import gdsc.plantory.plant.service.PlantService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/plants")
class PlantQueryApi(
    private val plantService: PlantService
) {

    @GetMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun lookup(
        @RequestPart(name = "request") request: CompanionPlantLookupRequest,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<MutableList<CompanionPlant>> {
        val companionPlants = plantService.lookup(request, deviceToken)
        return ResponseEntity.ok().body(companionPlants)
    }
}