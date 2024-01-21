package gdsc.plantory.common.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.FirebaseMessaging
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.util.Optional

// @Configuration
class FirebaseConfig(
    @Value("\${fcm.key.path}")
    private val fcmPrivateKeyPath: String,
    @Value("\${fcm.key.scope}")
    private val fcmScope: String
) {

    @Bean
    fun firebaseMessaging(): FirebaseMessaging {
        val defaultFirebaseApp = defaultFirebaseApp()

        if (defaultFirebaseApp.isPresent) {
            return FirebaseMessaging.getInstance(defaultFirebaseApp.get())
        }

        return FirebaseMessaging.getInstance(FirebaseApp.initializeApp(createFirebaseOption()))
    }

    private fun defaultFirebaseApp(): Optional<FirebaseApp> {
        val firebaseAppList = FirebaseApp.getApps()

        if (firebaseAppList == null || firebaseAppList.isEmpty()) {
            return Optional.empty()
        }

        return firebaseAppList.stream()
            .filter { firebaseApp: FirebaseApp -> firebaseApp.name == FirebaseApp.DEFAULT_APP_NAME }
            .findAny()
    }

    private fun createFirebaseOption(): FirebaseOptions {
        return FirebaseOptions.builder()
            .setCredentials(createGoogleCredentials())
            .build()
    }

    private fun createGoogleCredentials(): GoogleCredentials {
        return GoogleCredentials
            .fromStream(ClassPathResource(fcmPrivateKeyPath).inputStream)
            .createScoped(fcmScope)
    }
}
