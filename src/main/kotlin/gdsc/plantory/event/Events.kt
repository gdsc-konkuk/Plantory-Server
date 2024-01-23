package gdsc.plantory.event

import org.springframework.context.ApplicationEventPublisher
import java.util.Objects

class Events {
    companion object {
        private lateinit var publisher: ApplicationEventPublisher

        fun setPublisher(publisher: ApplicationEventPublisher) {
            Events.publisher = publisher
        }

        fun raise(event: Any) {
            if (Objects.nonNull(publisher)) {
                publisher.publishEvent(event)
            }
        }
    }
}
