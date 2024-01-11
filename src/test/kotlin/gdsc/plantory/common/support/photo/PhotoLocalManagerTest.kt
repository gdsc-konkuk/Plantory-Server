package gdsc.plantory.common.support.photo

import gdsc.plantory.fixture.FileFixture
import gdsc.plantory.util.ImageCleanerExtension
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.multipart.MultipartFile


@ExtendWith(ImageCleanerExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhotoLocalManagerTest(
    @Autowired private val photoManager: PhotoLocalManager
) {

    @Test
    fun `이미지를 성공적으로 업로드한다`() {
        // given
        val multipartFile: MultipartFile = FileFixture.generateMultiPartFile()
        val imagePath: String = FileFixture.IMAGE_PATH

        // when
        var imageUrl: String? = null
        Assertions.assertThatCode {
            imageUrl = photoManager.upload(multipartFile, imagePath)
        }.doesNotThrowAnyException()

        // then
        assertThat(imageUrl).isNotBlank()
    }
}
