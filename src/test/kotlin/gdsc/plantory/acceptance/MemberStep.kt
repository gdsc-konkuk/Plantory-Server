package gdsc.plantory.acceptance

import gdsc.plantory.member.dto.MemberSignUpRequest
import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import org.springframework.http.MediaType

class MemberStep {

    companion object {

        fun 회원_가입_요청(request: MemberSignUpRequest): ExtractableResponse<Response> =
            RestAssured.given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(request)
                .`when`().post("/api/v1/members")
                .then().log().all()
                .extract()
    }
}