package gdsc.plantory.common.support

import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class DeviceUserResolver : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: org.springframework.core.MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(AccessDeviceId::class.java)
    }

    override fun resolveArgument(
        parameter: org.springframework.core.MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        return DeviceHeaderExtractor.extractDeviceId(webRequest)
    }
}
