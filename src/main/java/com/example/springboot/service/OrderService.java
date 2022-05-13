package com.example.springboot.service;

import com.example.springboot.model.*;
import com.example.springboot.model.item.Item;
import com.example.springboot.repository.ItemRepository;
import com.example.springboot.repository.MemberRepository;
import com.example.springboot.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    // 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {

        // 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 아래와 같이도 생성이 가능하나 유지보수가 어려워짐
        // OrderItem 모델에서 @NoArgsConstructor 로 객체 생성 제약
        // OrderItem.OrderItem1 = new OrderItem();
        // orderItem1.setCount();

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // Order 모델에서 @NoArgsConstructor 로 새로운 객체 생성을 막음
        //new Order();

        // 주문 저장  : cascade 옵션으로 orderItem, delivery 는 동시에 저장됨
        orderRepository.save(order);
        return order.getId();
    }

    // 주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 엔티티 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();
    }
}
