package gdsc.plantory.common.support.photo

import org.springframework.util.StringUtils
import java.util.*

private const val UNDER_BAR: String = "_"
private const val DOT: String = "."
private const val UUID_BEGIN_INDEX: Int = 0
private const val UUID_END_INDEX: Int = 8

object PhotoNameGenerator {

    private val IMAGE_EXTENSIONS: Set<String> = setOf("jpeg", "jpg", "png", "webp", "heic", "heif")

    fun of(originalFilename: String?): String {
        validateFileName(originalFilename)
        return convertNameToPath(originalFilename!!)
    }

    private fun convertNameToPath(originalFilename: String): String {
        val extension = StringUtils.getFilenameExtension(originalFilename)
            ?: throw IllegalArgumentException("파일 확장자는 반드시 포함되어야 합니다. filename: $originalFilename")

        validateExtension(extension)

        val fileBaseName = UUID.randomUUID().toString().substring(UUID_BEGIN_INDEX, UUID_END_INDEX)

        return fileBaseName + UNDER_BAR + System.currentTimeMillis() + DOT + extension
    }

    private fun validateFileName(fileName: String?) {
        require(!fileName.isNullOrBlank()) { "파일 이름은 반드시 포함되어야 합니다. filename: $fileName" }
    }

    private fun validateExtension(extension: String) {
        require(IMAGE_EXTENSIONS.contains(extension.lowercase(Locale.getDefault()))) {
            "이미지 파일 확장자만 업로드 가능합니다. extension: $extension"
        }
    }
}
