package com.example.springboot.api;

import com.example.springboot.model.Member;
import com.example.springboot.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;


// @Controller  + @ResponseBody = @RestController
@RestController
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    // 등록 V1: 요청 값으로 Member 엔티티를 직접 받는다.
    // @RequestBody : json으로 온 body를 Member에 그대로 매핑해서 넣어줌
    // 문제: 엔티티를 변경하면 api 스펙 자체가 변하게됨 -> 회원 엔티티 - 다양한 api 연결 -> api 요구사항을 모두 엔티티에 담기 힘들다
    @PostMapping("/api/v1/members")

    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) {
        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    // 등록 V2: 엔티티 대신에 DTO 를 RequestBody 에 매핑
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {

        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    @Data
    static class CreateMemberRequest {
        // 필수값으로 하고 싶으면 엔티티가 아닌 이곳에서 지정해줌
        @NotEmpty
        private String name;
    }

    // 등록된 id 값 반환
    @Data
    static class CreateMemberResponse {
        private Long id;
        // 생성자
        public CreateMemberResponse(Long id) { this.id = id; }
    }

    // 수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateMemberRequest request) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    // 조회 V1: 응답 값(주소, 주문내역 등)으로 엔티티를 직접 외부에 노출하는 문제가 생김
    // 엔티티 필드에 @JsonIgnore 로 응답에서 제외시킬 수 있지만, 관리도 어렵고, 화면 요소가 엔티티에 들어오게됨(프레젠테이션)
    // 컬렉션을 그대로 반환하면 반환 폼을 변경하기가 어렵다
    @GetMapping("api/v1/members")
    public List<Member> membersV1() {
        return memberService.findMembers();
    }

    // 조회 V2: 응답 값으로 엔티티가 아닌 별도의 DTO 반환
    @GetMapping("api/v2/members")
    public Result membersV2() {
        List<Member> findMembers = memberService.findMembers();

        // 엔티티 -> DTO 변환
        List<MemberDto> collect = findMembers.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());
        return new Result(collect.size(), collect);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {
        private String name;
    }
}
