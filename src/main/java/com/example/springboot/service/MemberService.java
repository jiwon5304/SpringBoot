package com.example.springboot.service;

import com.example.springboot.repository.MemberRepository;
import com.example.springboot.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


// service: 비즈니스 로직, 트랜잭션 처리
@Service
// JPA 모든 데이터 변경은 가급적 트랜잭션 안에서 실행
// 기본적으로 읽기 전용 트랜잭션
@Transactional(readOnly = true)
public class MemberService {

    // 스프링이 스플링 빈에 등록되어있는 레파지토리를 인젝션
    // 생성자 주입 방식을 권장
    // final 권장: 변경할 일이 없으므로
    private final MemberRepository memberRepository;

    // 스프링 최신버전에서는 생성자가 하나만 있는 경우 @Autowired 없어도 가능
    // @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원가입
    // 1. save 하는 시점에 영속성컨텍스트에 Member 객체를 올림
    // 2.key -> DB의 pk(id), value
    // 3.DB 들어가 있지 않아도 member.getId() 값 있음이 보장됨
    @Transactional // readOnly = false
    public Long join(Member member) {
        validateDuplicateMember(member); //중복 회원 검증
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers =
                memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}

