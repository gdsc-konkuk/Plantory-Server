package gdsc.plantory.fixture

import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.imageio.ImageIO

object FileFixture {

    private val IMAGE = generateImage()

    fun generateImage(): ByteArray {
        val image = BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB)

        try {
            ByteArrayOutputStream().use { byteArrayOutputStream ->
                ImageIO.write(image, "jpg", byteArrayOutputStream)
                return byteArrayOutputStream.toByteArray()
            }
        } catch (e: IOException) {
            throw IllegalStateException("이미지 생성 실패")
        }
    }

    fun generateMultiPartFile(): MultipartFile {
        return MockMultipartFile(
            "image",
            "plantory.jpg",
            MediaType.IMAGE_JPEG_VALUE,
            IMAGE
        )
    }
}
