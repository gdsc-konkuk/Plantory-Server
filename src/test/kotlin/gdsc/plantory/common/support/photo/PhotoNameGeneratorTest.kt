package gdsc.plantory.common.support.photo

import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.NullAndEmptySource
import org.junit.jupiter.params.provider.ValueSource

@DisplayName("유닛 : PhotoNameGenerator")
class PhotoNameGeneratorTest {

    @Test
    fun `이미지 파일명 생성`() {
        // given
        val filename = "https://shine.image-1.png"

        // when, then
        assertThatCode {
            PhotoNameGenerator.of(filename)
        }.doesNotThrowAnyException()
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = [" "])
    fun `파일 이름이 없으면 예외를 반환`(filename: String?) {
        assertThatThrownBy { PhotoNameGenerator.of(filename) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("파일 이름은 반드시 포함되어야 합니다. filename: $filename")
    }

    @ParameterizedTest
    @ValueSource(strings = ["plantory", "shine"])
    fun `파일 확장자가 없으면 예외를 반환`(filename: String) {
        assertThatThrownBy { PhotoNameGenerator.of(filename) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("파일 확장자는 반드시 포함되어야 합니다. filename: $filename")
    }

    @ParameterizedTest
    @ValueSource(strings = ["test.pdf", "test.zip", "test.exe"])
    fun `이미지 파일 확장자가 아니면 예외를 반환`(filename: String) {
        assertThatThrownBy { PhotoNameGenerator.of(filename) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage(
                "이미지 파일 확장자만 업로드 가능합니다. extension: " +
                        filename
                            .split("\\.".toRegex())
                            .dropLastWhile { it.isEmpty() }
                            .toTypedArray()[1]
            )
    }
}
