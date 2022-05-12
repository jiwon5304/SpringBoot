package com.example.springboot.repository;

import com.example.springboot.model.OrderStatus;
import lombok.*;

@Getter @Setter
public class OrderSearch {

    private String memberName;          // 회원 이름
    private OrderStatus orderStatus;    // 주문 상태[ORDER, CANCEL]
}
