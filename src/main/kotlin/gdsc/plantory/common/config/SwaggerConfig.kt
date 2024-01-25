package gdsc.plantory.common.config

import io.swagger.v3.oas.models.parameters.Parameter
import org.springdoc.core.customizers.ParameterCustomizer
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestBody

@Component
class SwaggerConfig : ParameterCustomizer {

    override fun customize(parameterModel: Parameter?, methodParameter: MethodParameter?): Parameter? {
        if (methodParameter?.getParameterAnnotation(RequestBody::class.java) != null) {
            parameterModel
                ?.`in`("request body")
        }

        return parameterModel;
    }
}
