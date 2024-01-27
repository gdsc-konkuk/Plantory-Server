package gdsc.plantory.plant.presentation

import BadRequestException
import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.plant.domain.HistoryType
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoryRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordCreateRequest
import gdsc.plantory.plant.service.PlantService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@Tag(name = "Plant Command", description = "반려식물 정보 수정")
@RestController
@RequestMapping("/api/v1/plants")
class PlantCommandApi(
    private val plantService: PlantService,
) {

    @Operation(summary = "반려식물 등록", description = "사용자의 반려식물을 등록/추가합니다.")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE])
    fun create(
        @Parameter(description = "반려식물 정보") @RequestPart(name = "request") request: CompanionPlantCreateRequest,
        @Parameter(description = "반려식물 사진") @RequestPart(name = "image", required = false) image: MultipartFile?,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<Unit> {
        plantService.create(request, image, deviceToken)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "반려식물 삭제", description = "사용자의 반려식물을 삭제합니다.")
    @ApiResponse(responseCode = "204", description = "삭제 성공")
    @DeleteMapping("/{companionPlantId}")
    fun remove(
        @Parameter(description = "삭제할 반려식물 ID") @PathVariable companionPlantId: Long,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<Unit> {
        plantService.remove(companionPlantId, deviceToken)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "반려식물 히스토리 등록", description = "반려식물 히스토리(데일리 기록, 물줌, 분갈이)를 등록/추가합니다.")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping("/{companionPlantId}/histories", consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPlantHistory(
        @Parameter(description = "히스토리를 등록할 반려식물 ID") @PathVariable companionPlantId: Long,
        @Parameter(description = "히스토리 유형/종류") @RequestBody request: PlantHistoryRequest,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<Unit> {
        val historyType = HistoryType.byNameIgnoreCaseOrNull(request.historyType)
            ?: throw BadRequestException("잘못된 히스토리 타입입니다.")

        plantService.createPlantHistory(companionPlantId, deviceToken, historyType)
        return ResponseEntity.ok().build()
    }

    @Operation(summary = "반려식물 데일리 기록 등록", description = "반려식물 데일리 기록을 등록/추가합니다.")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping(
        "/{companionPlantId}/records",
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE]
    )
    fun createPlantRecord(
        @Parameter(description = "데일리 기록을 등록할 반려식물 ID") @PathVariable companionPlantId: Long,
        @Parameter(description = "데일리 기록 내용") @RequestPart request: PlantRecordCreateRequest,
        @Parameter(description = "데일리 기록 사진") @RequestPart(name = "image", required = false) image: MultipartFile?,
        @AccessDeviceToken deviceToken: String,
    ): ResponseEntity<Unit> {
        plantService.createRecord(companionPlantId, request, image, deviceToken)
        return ResponseEntity.ok().build()
    }
}
