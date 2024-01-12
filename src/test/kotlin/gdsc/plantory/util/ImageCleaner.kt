package gdsc.plantory.util

import java.io.File

private const val TEST_FILE_ROOT_PATH = "src/test/resources/"

class ImageCleaner {

    companion object {
        fun cleanUp() {
            deleteFolder(TEST_FILE_ROOT_PATH)
        }

        private fun deleteFolder(path: String) {
            val folder = File(path)
            if (folder.exists()) {
                deleteFileRecursive(folder)
            }
        }

        private fun deleteFileRecursive(file: File) {
            if (file.isDirectory) {
                val files = file.listFiles() ?: return
                for (child in files) {
                    deleteFileRecursive(child)
                }
            }
            if (file.name.contains("application.yml")) {
                return
            }
            file.delete()
        }
    }
}
