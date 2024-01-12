package gdsc.plantory.plant.presentation

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.service.PlantService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/plants")
class PlantCommandApi(
    private val plantService: PlantService,
) {

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun create(
        @RequestPart(name = "request") request: CompanionPlantCreateRequest,
        @RequestPart(name = "image", required = false) image: MultipartFile?,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<Unit> {
        plantService.create(request, image, deviceToken)
        return ResponseEntity.ok().build()
    }
}
