package com.example.springboot.repository;

import com.example.springboot.model.Order;
import com.example.springboot.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    // 주문 저장
    public void save(Order order) { em.persist(order); }

    // 주문 단건 조회
    public Order findOne(Long id) { return em.find(Order.class, id); }

    // 다중 조회
    public List<Order> findAll() {
        return em.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    // 검색 : JPQL or JPA Criteria or Querydsl 로 처리
    // JPQL
    public List<Order> findAllByString(OrderSearch orderSearch){

        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) {
                jpql += " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
                .setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }

        return query.getResultList();
    }

    // JPA Criteria
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Object, Object> m = o.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"), orderSearch.getOrderStatus());
            criteria.add(status);
        }
        //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name =
                    cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }

        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
    }

    // 추후에 Querydsl 로 추가 예정

    // fetch join: Lazy 이지만 무시하고, 진짜 객체의 값을 다 채워서 가지고 옴
    public List<Order> findAllWithMemberDelivery() {
        return em.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +              // join fetch: order 를 가져올 때 member 까지 한번에 객체 그래프로 가져옴
                        " join fetch o.delivery d", Order.class)
                .getResultList();
    }
}

