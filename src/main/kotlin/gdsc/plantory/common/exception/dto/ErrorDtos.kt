import org.springframework.http.HttpStatus

data class ErrorResponse(
    val message: String?,
)

open class PlantoryException(override val message: String, val status: Int) : RuntimeException()

class NotFoundException(message: String = "존재하지 않는 요청입니다.") : PlantoryException(message, HttpStatus.NOT_FOUND.value())

class ConflictException(message: String = "이미 등록된 디바이스 입니다.") : PlantoryException(message, HttpStatus.CONFLICT.value())

class BadRequestException(message: String = "잘못된 요청입니다.") : PlantoryException(message, HttpStatus.BAD_REQUEST.value())
