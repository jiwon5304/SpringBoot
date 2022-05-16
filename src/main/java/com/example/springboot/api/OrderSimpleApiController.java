package com.example.springboot.api;

import com.example.springboot.model.*;
import com.example.springboot.repository.*;
import com.example.springboot.repository.order.simplequery.*;
import lombok.*;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 xToOne(ManyToOne, OneToOne) 관계에서 성능 최적화
 * Order
 * Order -> Member
 * Order -> Delivery
 **/
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    /**
     * 양방향 관계 문제 발생 -> 무한루프(member-order / orderitem-order / delivery-order): @JsonIgnore
     * Hibernate5Module 모듈 등록 /
     * 지연로딩 -> 프록시존재 -> Lazy 강제 초기화
     **/

    //조회 V1: 엔티티를 직접 노출
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName();        // ~getMember() 까지는 프록시이나, name 값을 가지고 오면서 Lazy 강제 초기화
            order.getDelivery().getAddress();   //Lazy 강제 초기화
        }
        return all;
    }

    // 조회 V2: 엔티티를 DTO로 변환
    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        /**
         * 총 5번의 쿼리 : N + 1 문제 발생(최악의경우 Why? 영속성 컨텍스트에 없을 때) -> 1 + N(2,회원) + N(2,배송)
         * 첫번째 주문서: SimpleOrderDto(o)의 member 쿼리(name), delivery 쿼리(address) -> 2번
         * 두번째 주문서: SimpleOrderDto(o)의 member 쿼리(name), delivery 쿼리(address) -> 2번
         * 해결하고자 EAGER를 사용하면 알 수 없는 쿼리가 발생함 -> 페치조인으로 해결
         **/

        List<Order> orders = orderRepository.findAll();

        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))            // order를 SimpleOrderDto로 바꿈
                .collect(toList());                         // colloect 로 해서 List로 변경

        return result;

    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();     // Lazy 초기화 : 영속성 컨텍스트가 id 찾으면서 없으면 DB 쿼리 날림
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress(); // Lazy 초기화 : 영속성 컨텍스트가 id 찾으면서 없으면 DB 쿼리 날림
        }
    }

    // 조회 V3: 엔티티를 DTO로 변환 - 페치 조인 최적화
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> ordersV3() {
        List<Order> orders = orderRepository.findAllWithMemberDelivery();
        List<SimpleOrderDto> result = orders.stream()
                .map(o -> new SimpleOrderDto(o))
                .collect(toList());
        return result;
    }

    // 조회 V4: JPA에서 DTO로 바로 조회, 페치조인보다 성능 최적화, 재활용이 불가능
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;        // 의존관계 주입
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDto();
    }

}
