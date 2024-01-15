package gdsc.plantory.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus

class CommonStep {

    companion object {

        fun 응답_확인(response: ExtractableResponse<Response>, httpStatus: HttpStatus) =
            assertThat(response.statusCode()).isEqualTo(httpStatus.value())
    }
}
