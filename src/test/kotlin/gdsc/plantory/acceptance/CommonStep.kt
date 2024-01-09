package gdsc.plantory.acceptance

import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.HttpStatus

class CommonStep {

    companion object {

        fun 응답_확인(응답: ExtractableResponse<Response>, httpStatus: HttpStatus) =
            assertThat(응답.statusCode()).isEqualTo(httpStatus.value())
    }
}
