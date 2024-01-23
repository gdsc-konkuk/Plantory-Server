package gdsc.plantory.common.support

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.context.request.NativeWebRequest

@DisplayName("유닛 : DeviceHeaderExtractor")
class DeviceHeaderExtractorTest {

    @Test
    fun `디바이스 ID 추출`() {
        // given
        val request = mockk<NativeWebRequest>()
        val expected = "shine"
        every { request.getHeader("Device-Token") } returns expected

        // when
        val actual = DeviceHeaderExtractor.extractDeviceToken(request)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `디바이스 ID가 존재하지 않으면 예외를 반환`() {
        // given
        val request = mockk<NativeWebRequest>()
        every { request.getHeader("Device-Token") } returns null

        // when, then
        assertThrows<IllegalArgumentException> { DeviceHeaderExtractor.extractDeviceToken(request) }
    }
}
