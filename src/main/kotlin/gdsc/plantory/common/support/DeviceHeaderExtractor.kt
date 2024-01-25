package gdsc.plantory.common.support

import org.springframework.web.context.request.NativeWebRequest

const val DEVICE_ID_HEADER = "Device-Token"

class DeviceHeaderExtractor {

    companion object {
        fun extractDeviceToken(request: NativeWebRequest): String {
            return request.getHeader(DEVICE_ID_HEADER)
                ?: throw IllegalArgumentException("디바이스 Token이 존재하지 않습니다.")
        }
    }
}
