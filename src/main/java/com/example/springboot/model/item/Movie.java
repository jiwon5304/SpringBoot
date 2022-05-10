package com.example.springboot.model.item;

import lombok.*;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
// 하위 클래스에 선언, 엔티티를 저장할 때 슈퍼타입의 구분 컬럼에 저장할 값을 지정
@DiscriminatorValue("M")
@Getter @Setter
public class Movie extends Item {
    private String director;
    private String actor;
}
