package gdsc.plantory.common.config

import gdsc.plantory.event.Events
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EventsConfiguration(
    @Autowired val applicationContext: ApplicationContext,
) {

    @Bean
    fun eventsInitializer(): InitializingBean {
        return InitializingBean { Events.setPublisher(applicationContext) }
    }
}
