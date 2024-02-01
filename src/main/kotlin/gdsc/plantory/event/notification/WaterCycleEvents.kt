package gdsc.plantory.event.notification

data class WaterCycleEvents(
    val plantsNeedWateredToday: List<WaterCycleEvent>
)