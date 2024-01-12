package gdsc.plantory.common.support.photo

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.io.IOException

@Component
class PhotoLocalManager(
    @Value("\${local.image.root}")
    private val localPath: String,
    @Value("\${companionPlant.image.directory}")
    private var workingDirectory: String,
) {

    companion object {
        private const val SLASH = "/"
    }

    fun upload(multipartFile: MultipartFile): String? {
        require(!(multipartFile.isEmpty)) { "이미지 파일이 존재하지 않습니다" }
        return uploadPhoto(multipartFile)
    }

    private fun uploadPhoto(multipartFile: MultipartFile): String? {
        return try {
            val fileName: String = PhotoNameGenerator.of(multipartFile.originalFilename)
            val uploadDirectory = loadDirectory(getLocalDirectoryPath(workingDirectory))
            val uploadPath = File(uploadDirectory, fileName)

            uploadFileInLocal(multipartFile, uploadPath)

            StringBuilder()
                .append(SLASH)
                .append(workingDirectory)
                .append(SLASH)
                .append(fileName)
                .toString()
        } catch (e: Exception) {
            throw IllegalStateException("파일 업로드 실패")
        }
    }

    private fun getLocalDirectoryPath(workingDirectory: String): String {
        return StringBuilder()
            .append(localPath)
            .append(SLASH)
            .append(workingDirectory)
            .toString()
    }

    private fun loadDirectory(directoryLocation: String): File {
        val directory = File(directoryLocation)
        if (!directory.exists()) {
            directory.mkdirs()
        }
        return directory
    }

    private fun uploadFileInLocal(multipartFile: MultipartFile, uploadPath: File) {
        try {
            multipartFile.transferTo(uploadPath)
        } catch (e: IOException) {
            throw IllegalStateException("파일 변환이 실패했습니다.")
        }
    }
}
