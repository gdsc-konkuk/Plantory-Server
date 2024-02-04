package gdsc.plantory.plant.presentation

import gdsc.plantory.event.Events
import gdsc.plantory.event.notification.WaterCycleEvent
import gdsc.plantory.event.notification.WaterCycleEvents
import gdsc.plantory.plant.presentation.dto.AlarmRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Test Alarm", description = "테스트 알림 API")
@RestController
@RequestMapping("/api/v1/alarm")
class TempApi {

    // TODO : 꼭 삭제할것
    @Operation(summary = "알림 테스트 전송", description = "테스트 용도의 알림 전송 API")
    @ApiResponse(responseCode = "200", description = "알림 전송 성공")
    @PostMapping
    fun AlarmApi(
        @RequestBody request: AlarmRequest
    ): ResponseEntity<Unit> {
        val event = WaterCycleEvent(request.deviceToken)
        val events: List<WaterCycleEvent> = listOf(event)
        Events.raise(WaterCycleEvents(events))

        return ResponseEntity.ok().build()
    }
}
