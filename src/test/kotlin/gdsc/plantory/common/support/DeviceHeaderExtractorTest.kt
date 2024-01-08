package gdsc.plantory.common.support

import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.web.context.request.NativeWebRequest

class DeviceHeaderExtractorTest {

    @Test
    fun `디바이스 ID를 추출한다`() {
        // given
        val request = mockk<NativeWebRequest>()
        val expected = "shine"
        every { request.getHeader("Device-Id") } returns expected

        // when
        val actual = DeviceHeaderExtractor.extractDeviceId(request)

        // then
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `디바이스 ID가 존재하지 않으면 예외를 반환한다`() {
        // given
        val request = mockk<NativeWebRequest>()
        every { request.getHeader("Device-Id") } returns null

        // when, then
        assertThrows<IllegalArgumentException> { DeviceHeaderExtractor.extractDeviceId(request) }
    }
}
