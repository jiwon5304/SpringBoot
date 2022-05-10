package com.example.springboot.model.item;

import com.example.springboot.model.Category;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
// 상속관계 매핑 전략(JOINED / SINGLE_TABLE / TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// 부모 클래스에 선언
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    // Item 에서 OrderItem 을 조회할 필요가 없으므로 관계 지정하지 않음.

    // 실무에서는 ManyToMany 를 사용하지 않도록 한다.
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();
}
