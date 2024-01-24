package gdsc.plantory.common.exception

import ErrorResponse
import PlantoryException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.multipart.MaxUploadSizeExceededException

@RestControllerAdvice
class CommonExceptionHandler {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)!!
    }

    @ExceptionHandler
    fun handleBadRequestException(ex: PlantoryException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(ex.status).body(ErrorResponse(ex.message))
    }

    @ExceptionHandler
    fun unHandleException(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        log.error("서버 에러 발생! ${request.method} ${request.requestURI} ${ex.message}")

        return ResponseEntity.internalServerError().body(ErrorResponse("서버 에러가 발생했습니다. 관리자에게 문의해주세요."))
    }

    @ExceptionHandler
    fun handleMaxSizeException(ex: MaxUploadSizeExceededException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(ErrorResponse("파일의 최대 사이즈를 확인해주세요."))
    }
}
