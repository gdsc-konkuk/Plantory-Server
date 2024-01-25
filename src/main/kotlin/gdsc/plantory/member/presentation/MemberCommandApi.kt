package gdsc.plantory.member.presentation

import gdsc.plantory.member.presentation.dto.MemberCreateRequest
import gdsc.plantory.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/members")
class MemberCommandApi(
    private val memberService: MemberService,
) {

    @Operation(summary = "사용자 등록", description = "사용자를 등록/추가합니다.")
    @ApiResponse(responseCode = "200", description = "등록 성공")
    @PostMapping
    fun signUp(
        @Parameter(description = "사용자 정보") @RequestBody request: MemberCreateRequest,
    ): ResponseEntity<Unit> {
        memberService.signUp(request.deviceToken)
        return ResponseEntity.ok().build()
    }
}
