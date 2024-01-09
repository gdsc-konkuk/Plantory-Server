package gdsc.plantory.member.presentation

import gdsc.plantory.member.dto.MemberSignUpRequest
import gdsc.plantory.member.service.MemberService
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

    @PostMapping
    fun signUp(
        @RequestBody request: MemberSignUpRequest,
    ): ResponseEntity<Unit> {
        memberService.signUp(request.deviceToken)
        return ResponseEntity.ok().build()
    }
}
