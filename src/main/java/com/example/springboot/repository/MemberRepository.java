package com.example.springboot.repository;

import com.example.springboot.model.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// repository : JPA 를 직접 사용하는 계층, 엔티티 매니저 사용

// 스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 예외 변환
@Repository
public class MemberRepository {
    // 스프링이 EntityManager 만들어서 인젝션(주입)
    @PersistenceContext
    private EntityManager em;

    // 저장
    // 1. 영속성 컨텍스트안에 멤버 객체 넣기
    // 2. 트랜잭션 커밋되는 시점에 DB에 반영됨
    public void save(Member member) { em.persist(member); }

    // 단건 조회
    // JPA find 메소드 사용, find(타입, pk)
    public Member findOne(Long id) { return em.find(Member.class, id); }

    // 리스트 조회
    // createQuery(JPQL, 반환타입)
    // JPQL: 객체(Entity) 대상으로 쿼리, Member 객체
    // SQL: 테이블을 대상을 쿼리
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList(); }

    // 이름으로 회원 검색
    // 파라미터로 이름 전달
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}