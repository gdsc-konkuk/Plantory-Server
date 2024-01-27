package gdsc.plantory.common.config

import gdsc.plantory.common.support.AccessDeviceToken
import gdsc.plantory.common.support.DEVICE_ID_HEADER
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.security.SecurityRequirement
import org.springdoc.core.customizers.OperationCustomizer
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod

@Component
class Operation : OperationCustomizer {
    override fun customize(operation: Operation?, handlerMethod: HandlerMethod?): Operation? {
        if (handlerMethod?.methodParameters?.find { it.hasParameterAnnotation(AccessDeviceToken::class.java) } != null) {
            operation?.addSecurityItem(SecurityRequirement().addList(DEVICE_ID_HEADER))
        }

        return operation
    }
}