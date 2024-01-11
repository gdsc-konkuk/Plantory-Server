package gdsc.plantory.acceptance

import gdsc.plantory.fixture.CompanionPlantFixture
import gdsc.plantory.plant.presentation.dto.CompanionPlantCreateRequest
import gdsc.plantory.util.AcceptanceTest
import io.restassured.RestAssured
import io.restassured.builder.MultiPartSpecBuilder
import io.restassured.mapper.ObjectMapperType
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import io.restassured.specification.MultiPartSpecification
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@DisplayName("인수 : CompanionPlant")
class CompanionPlantAcceptanceTest : AcceptanceTest() {

    @Test
    fun `사용자의 반려 식물 등록`() {
        // given
        val createRequest = CompanionPlantFixture.generatePetPlantCreateRequest(1L)

        // when
        val 식물_등록_요청_응답 = 반려_식물_등록_요청(createRequest, "device_id")

        // then
        CommonStep.응답_확인(식물_등록_요청_응답, HttpStatus.OK)
    }

    private fun 반려_식물_등록_요청(
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
            .post("/v1/plants")
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
