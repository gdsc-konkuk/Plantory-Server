package gdsc.plantory.plant.presentation

import BadRequestException
import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.CompanionPlantDeleteRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordCreateRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoryRequest
import gdsc.plantory.plant.service.PlantService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/v1/plants")
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

    @DeleteMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun remove(
        @RequestBody request: CompanionPlantDeleteRequest,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<Unit> {
        plantService.remove(request, deviceToken)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/histories", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPlantHistory(
        @RequestBody request: PlantHistoryRequest,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<Unit> {
        val historyType = HistoryType.byNameIgnoreCaseOrNull(request.historyType)
            ?: throw BadRequestException("잘못된 히스토리 타입입니다.")

        plantService.createPlantHistory(request.companionPlantId, deviceToken, historyType)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/records", consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun createPlantRecord(
        @RequestPart(name = "request") request: PlantRecordCreateRequest,
        @RequestPart(name = "image", required = false) image: MultipartFile?,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<Unit> {
        plantService.createRecord(request, image, deviceToken)
        return ResponseEntity.ok().build()
    }
}
