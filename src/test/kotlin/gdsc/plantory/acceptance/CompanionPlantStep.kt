package gdsc.plantory.acceptance

import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.plant.presentation.dto.PlantHistoryRequest
import gdsc.plantory.plant.presentation.dto.PlantRecordCreateRequest
import io.restassured.RestAssured
import io.restassured.builder.MultiPartSpecBuilder
import io.restassured.mapper.ObjectMapperType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import io.restassured.specification.MultiPartSpecification
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertAll
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.time.LocalDate
import java.time.YearMonth

class CompanionPlantStep {

    companion object {
        fun 반려_식물_등록_요청(
            request: CompanionPlantCreateRequest,
            deviceToken: String,
        ): ExtractableResponse<Response> {
            val multipartData: MultiPartSpecification = getMultiPartSpecification(request)

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

        fun 반려_식물_삭제_요청(
            companionPlantId: Long,
            deviceToken: String,
        ): ExtractableResponse<Response> {
            return RestAssured
                .given()
                .header("Device-Token", deviceToken)
                .log().all()
                .`when`()
                .delete("/api/v1/plants/{companionPlantId}", companionPlantId)
                .then()
                .log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract()
        }

        fun 식물_히스토리_생성_요청(
            companionPlantId: Long,
            request: PlantHistoryRequest,
            deviceToken: String,
        ): ExtractableResponse<Response> =
            RestAssured
                .given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Device-Token", deviceToken)
                .log().all()
                .body(request)
                .`when`().post("/api/v1/plants/{companionPlantId}/histories", companionPlantId)
                .then()
                .log().all()
                .extract()

        fun 식물_조회_응답_확인(response: ExtractableResponse<Response>) {
            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(response.jsonPath().getString("companionPlants[].id")).isNotBlank() },
                { assertThat(response.jsonPath().getString("companionPlants[].imageUrl")).isNotBlank() },
                { assertThat(response.jsonPath().getString("companionPlants[].nickname")).isNotBlank() },
                {
                    assertThat(response.jsonPath().getString("companionPlants[].shortDescription")).isNotBlank()
                },
                { assertThat(response.jsonPath().getString("companionPlants[].birthDate")).isNotBlank() },
                { assertThat(response.jsonPath().getString("companionPlants[].name")).isNotBlank() },
            )
        }

        fun 식물_조회_요청(
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
            companionPlantId: Long,
            request: PlantRecordCreateRequest,
            deviceToken: String,
        ): ExtractableResponse<Response> {
            val multipartData: MultiPartSpecification = getMultiPartSpecification(request)

            return RestAssured
                .given()
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .multiPart(multipartData)
                .header("Device-Token", deviceToken)
                .log().all()
                .`when`()
                .post("/api/v1/plants/{companionPlantId}/records", companionPlantId)
                .then()
                .log().all()
                .extract()
        }

        fun 데일리_기록_조회_요청(
            companionPlantId: Long,
            recordDate: LocalDate,
            deviceToken: String
        ): ExtractableResponse<Response> {
            return RestAssured
                .given()
                .header("Device-Token", deviceToken)
                .log().all()
                .queryParam("recordDate", recordDate.toString())
                .`when`()
                .get("/api/v1/plants/{companionPlantId}/records", companionPlantId)
                .then()
                .log().all()
                .extract()
        }

        fun 데일리_기록_조회_응답_확인(response: ExtractableResponse<Response>) {
            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(response.jsonPath().getString("plantRecordId")).isNotBlank() },
                { assertThat(response.jsonPath().getString("imageUrl")).isNotBlank() },
                { assertThat(response.jsonPath().getString("comment")).isNotBlank() },
                { assertThat(response.jsonPath().getString("nickname")).isNotBlank() },
                { assertThat(response.jsonPath().getString("water")).isNotBlank() },
            )
        }

        fun 히스토리_조회_요청(
            companionPlantId: Long,
            targetMonth: YearMonth,
            deviceToken: String
        ): ExtractableResponse<Response> {
            return RestAssured
                .given()
                .header("Device-Token", deviceToken)
                .log().all()
                .queryParam("targetMonth", targetMonth.toString())
                .`when`()
                .get("/api/v1/plants/{companionPlantId}/histories", companionPlantId)
                .then()
                .log().all()
                .extract()
        }

        fun 히스토리_조회_응답_확인(response: ExtractableResponse<Response>) {
            assertAll(
                { assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value()) },
                { assertThat(response.jsonPath().getString("histories.id")).isNotBlank() },
                { assertThat(response.jsonPath().getString("histories.type")).isNotBlank() },
                { assertThat(response.jsonPath().getString("histories.date")).isNotBlank() },
            )
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
