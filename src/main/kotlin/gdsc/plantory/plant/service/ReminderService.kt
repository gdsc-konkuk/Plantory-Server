package gdsc.plantory.plant.service

import gdsc.plantory.event.Events
import gdsc.plantory.event.notification.WaterCycleEvent
import gdsc.plantory.event.notification.WaterCycleEvents
import gdsc.plantory.plant.domain.CompanionPlantRepository
import gdsc.plantory.plant.presentation.dto.CompanionPlantWaterCycleDto
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@Transactional
class ReminderService(
    private val companionPlantRepository: CompanionPlantRepository,
) {

    @Scheduled(cron = "0 0 8 * * *")
    fun sendWaterNotification() {
        val companionPlants = companionPlantRepository.findAllByNextWaterDate(LocalDate.now())
        val events = WaterCycleEvents(buildWaterCycleEvents(companionPlants))
        Events.raise(events)
    }

    private fun buildWaterCycleEvents(companionPlants: List<CompanionPlantWaterCycleDto>) =
        companionPlants
            .map {
                WaterCycleEvent(
                    it.deviceToken,
                    "물을 줄 시간이에요!",
                    "${it.nickName}에게 물을 줄 시간이에요!"
                )
            }
            .toList()
}
