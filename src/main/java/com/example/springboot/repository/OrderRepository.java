package com.example.springboot.repository;

import com.example.springboot.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    // 주문 저장
    public void save(Order order) {
        em.persist(order);
    }

    // 주문 단건 조회
    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    // 검색 : JPQL 로 처리
    // 검색 : JPA Criteria 로 처리

    // 검색 : Querydsl 로 처리

}

