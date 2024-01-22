package gdsc.plantory.event.notification

data class WaterCycleEvent(
    val deviceToken: String,
    val title: String = "물을 줄 시간이에요!",
    val body: String = "반려 식물에게 물을 줄 시간이에요!",
)
