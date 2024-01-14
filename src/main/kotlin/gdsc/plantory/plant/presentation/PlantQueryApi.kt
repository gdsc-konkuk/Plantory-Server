package gdsc.plantory.plant.presentation

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.presentation.dto.CompanionPlantResponse
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

    @GetMapping()
    fun lookup(@AccessDeviceToken deviceToken: String): ResponseEntity<List<CompanionPlantResponse>> {
        val companionPlants = plantService.lookup(deviceToken)
        return ResponseEntity.ok().body(companionPlants.map {
            CompanionPlantResponse(
                it.getId,
                it.getImageUrl,
                it.getNickName,
                it.getSortDescription,
                it.getBirthDate
            )
        })
    }
}