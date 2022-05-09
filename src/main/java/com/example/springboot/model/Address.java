package com.example.springboot.model;

import lombok.Getter;
import javax.persistence.*;


// 값 타입은 변경이 되어서는 안됨
// 좋은 설계 : 생성할 때만 값이 생성되고, Setter를 제공하지 않도록 함.
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    // 기본 생성자를 만들어줌.
    // public은 사람들이 호출할 수 있으므로, protected 사용
    protected Address() {

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

}
