package gdsc.plantory.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus

class PlantInformationStep {
    companion object {
        fun 식물_정보_조회_요청(): ExtractableResponse<Response> {
            return RestAssured
                .given()
                .log().all()
                .`when`()
                .get("/api/v1/plantInformations")
                .then()
                .log().all()
                .extract()
        }

        fun 식물_정보_조회_응답_확인(response: ExtractableResponse<Response>) {
            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(response.jsonPath().getString("plantInformations[].id")).isNotBlank() },
                { assertThat(response.jsonPath().getString("plantInformations[].species")).isNotBlank() },
                { assertThat(response.jsonPath().getString("plantInformations[].familyName")).isNotBlank() },
            )
        }
    }
}
