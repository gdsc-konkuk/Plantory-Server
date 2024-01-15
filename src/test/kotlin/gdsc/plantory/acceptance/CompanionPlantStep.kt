package gdsc.plantory.acceptance

import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordCreateRequest
import gdsc.plantory.plant.presentation.dto.CompanionPlantHistoryRequest
import io.restassured.RestAssured
import io.restassured.builder.MultiPartSpecBuilder
import io.restassured.mapper.ObjectMapperType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import io.restassured.specification.MultiPartSpecification
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class CompanionPlantStep {

    companion object {
        fun 반려_식물_등록_요청(
            createRequest: CompanionPlantCreateRequest,
            deviceToken: String,
        ): ExtractableResponse<Response> {
            val multipartData: MultiPartSpecification = getMultiPartSpecification(createRequest)

            return RestAssured
                .given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart(multipartData)
                .header("Device-Token", deviceToken)
                .log().all()
                .`when`()
                .post("/api/v1/plants")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
        }

        fun 반려_식물_히스토리_생성_요청(
            request: CompanionPlantHistoryRequest,
            deviceToken: String,
        ): ExtractableResponse<Response> =
            RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Device-Token", deviceToken)
                .log().all()
                .body(request)
                .`when`().post("/api/v1/plants/histories")
                .then()
                .log().all()
                .extract()

        fun 조회_응답_확인(식물_조회_요청_응답: ExtractableResponse<Response>) {
            assertAll(
                { Assertions.assertThat(식물_조회_요청_응답.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { Assertions.assertThat(식물_조회_요청_응답.jsonPath().getString("companionPlants.id")).isNotBlank() },
                { Assertions.assertThat(식물_조회_요청_응답.jsonPath().getString("companionPlants.imageUrl")).isNotBlank() },
                { Assertions.assertThat(식물_조회_요청_응답.jsonPath().getString("companionPlants.nickname")).isNotBlank() },
                {
                    Assertions.assertThat(식물_조회_요청_응답.jsonPath().getString("companionPlants.shortDescription"))
                        .isNotBlank()
                },
                { Assertions.assertThat(식물_조회_요청_응답.jsonPath().getString("companionPlants.birthDate")).isNotBlank() },
            )
        }

        fun 반려_식물_조회_요청(
            deviceToken: String,
        ): ExtractableResponse<Response> {
            return RestAssured
                .given()
                .header("Device-Token", deviceToken)
                .log().all()
                .`when`()
                .get("/api/v1/plants")
                .then()
                .log().all()
                .extract()
        }

        fun 데일리_기록_등록_요청(
            createRequest: PlantRecordCreateRequest,
            deviceToken: String,
        ): ExtractableResponse<Response> {
            val multipartData: MultiPartSpecification = getMultiPartSpecification(createRequest)

            return RestAssured
                .given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart(multipartData)
                .header("Device-Token", deviceToken)
                .log().all()
                .`when`()
                .post("/api/v1/plants/${1}/records")
                .then()
                .log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
        }

        private fun getMultiPartSpecification(request: Any): MultiPartSpecification {
            return MultiPartSpecBuilder(request, ObjectMapperType.JACKSON_2)
                .controlName("request")
                .mimeType(MediaType.APPLICATION_JSON_VALUE)
                .charset("UTF-8")
                .with()
                .build()
        }
    }
}
