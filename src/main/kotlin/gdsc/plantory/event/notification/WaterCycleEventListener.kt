package gdsc.plantory.event.notification

import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.AndroidNotification
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingException
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import gdsc.plantory.event.FCMChannel
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class WaterCycleEventListener(
    val firebaseMessaging: FirebaseMessaging,
) {

    companion object {
        private val log = LoggerFactory.getLogger(WaterCycleEventListener::class.java)
    }

    @Async
    @EventListener
    fun sendFcmNotification(events: WaterCycleEvents) {
        log.info("send FCM notification by WaterCycleEventListener")
        val messages: List<Message> = createMessages(events.plantsNeedWateredToday, FCMChannel.WATER_ALERT.name)

        try {
            if (messages.isEmpty()) return

            firebaseMessaging.sendEach(messages)
        } catch (e: FirebaseMessagingException) {
            log.warn("fail send FCM message", e)
        }
    }

    private fun createMessages(events: List<WaterCycleEvent>, channelId: String): List<Message> {
        return events.map { event -> createMessage(event, channelId) }.toList()
    }

    private fun createMessage(event: WaterCycleEvent, channelId: String): Message {
        return Message.builder()
            .setNotification(
                Notification
                    .builder()
                    .setTitle(event.title)
                    .setBody(event.body)
                    .build()
            )
            .setAndroidConfig(createAndroidConfig(channelId))
            .setToken(event.deviceToken)
            .build()
    }

    private fun createAndroidConfig(channelId: String): AndroidConfig? {
        return AndroidConfig.builder()
            .setNotification(createAndroidNotification(channelId))
            .build()
    }

    private fun createAndroidNotification(channelId: String): AndroidNotification? {
        return AndroidNotification.builder()
            .setChannelId(channelId)
            .build()
    }
}
