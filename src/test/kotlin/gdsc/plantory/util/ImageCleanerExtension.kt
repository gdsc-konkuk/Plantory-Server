package gdsc.plantory.util

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class ImageCleanerExtension : AfterEachCallback {

    override fun afterEach(context: ExtensionContext?) {
        ImageCleaner.cleanUp()
    }
}
