package gdsc.plantory.common.support.photo

import gdsc.plantory.fixture.FileFixture
import gdsc.plantory.util.ImageCleanerExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.multipart.MultipartFile

@DisplayName("유닛 : PhotoLocalManager")
@ExtendWith(ImageCleanerExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PhotoLocalManagerTest(
    @Autowired private val photoManager: PhotoLocalManager
) {

    @Test
    fun `이미지 업로드`() {
        // given
        val multipartFile: MultipartFile = FileFixture.generateMultiPartFile()

        // when
        val imageUrl = photoManager.upload(multipartFile)

        // then
        assertThat(imageUrl).isNotBlank()
    }
}
