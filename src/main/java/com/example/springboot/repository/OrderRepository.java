package com.example.springboot.repository;

import com.example.springboot.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;


@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    // 주문 저장
    public void save(Order order) { em.persist(order); }

    // 주문 단건 조회
    public Order findOne(Long id) { return em.find(Order.class, id); }

    // 검색 : JPQL or JPA Criteria or Querydsl 로 처리
    // 추후에 Querydsl 로 추가 예쩡

}

