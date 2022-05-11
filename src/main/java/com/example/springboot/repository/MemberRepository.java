package com.example.springboot.repository;

import com.example.springboot.model.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    // 저장
    public void save(Member member) { em.persist(member); }

    // 단건 조회
    public Member findOne(Long id) { return em.find(Member.class ,id); }

    // 리스트 조회
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    // 이름으로 회원 검색
    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}