package com.example.springboot.model;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "libraries")
public class Libraries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user", insertable = false, updatable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book", insertable = false, updatable = false)
    private Books books;

    // to do : boolean type 필드추가하기
//    @Convert(converter = BooleanToYNConverter.class)
//    private boolean isActive;
}




