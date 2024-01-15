package gdsc.plantory.member.presentation

import gdsc.plantory.member.presentation.dto.MemberCreateRequest
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
        @RequestBody request: MemberCreateRequest,
    ): ResponseEntity<Unit> {
        memberService.signUp(request.deviceToken)
        return ResponseEntity.ok().build()
    }
}
