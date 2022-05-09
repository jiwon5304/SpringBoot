package com.example.springboot.model;

import com.example.springboot.model.item.Item;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // ManyToMany 는 실무에서 거의 사용하지 못함.
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),    // 중간테이블의 카테고리 id
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 아이템쪽으로 들어감
    private List<Item> items = new ArrayList<>();

    // 부모필드생성
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;

    // 자식필드생성
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //==연관관계 메서드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
