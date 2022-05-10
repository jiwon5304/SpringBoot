package com.example.springboot.service;

import com.example.springboot.model.Member;
import com.example.springboot.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.transaction.Transactional;
import static org.junit.Assert.*;


@RunWith(SpringRunner.class)            // 스프링이랑 같이 엮어서 실행
@SpringBootTest                         // 스프링부트를 띄운 상태로 실행
@Transactional                          // 트랜잭션 걸어서 실행 후 롤백
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    // @Rollback(value = false) DB에 들어감
    public void 회원가입() throws Exception {
        //given: 이렇게 주어졌을 때
        Member member = new Member();
        member.setName("kim");

        //when: 이렇게 하면
        Long saveId = memberService.join(member);

        //then: 이렇게 된다
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

        // @Test (expected = IllegalStateException.class) 하면 아래 제외해도 됨
        // try {
        //     memberService.join(member2);  // 예외가 발생해야 한다
        // } catch (IllegalStateException e) {
        //     return;
        // }

        //then
        fail("예외가 발생해야 한다");
    }
}