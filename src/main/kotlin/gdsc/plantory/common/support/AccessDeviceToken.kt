package gdsc.plantory.common.support

import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Parameter(`in` = ParameterIn.HEADER, name = DEVICE_ID_HEADER)
annotation class AccessDeviceToken
