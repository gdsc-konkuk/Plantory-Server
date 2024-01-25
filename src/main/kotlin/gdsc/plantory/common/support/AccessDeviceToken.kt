package gdsc.plantory.common.support

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Parameter(`in` = ParameterIn.HEADER, name = DEVICE_ID_HEADER, description = "사용자 디바이스/인증 토큰")
annotation class AccessDeviceToken
