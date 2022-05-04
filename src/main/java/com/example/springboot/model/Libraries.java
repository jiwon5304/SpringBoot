package com.example.springboot.model;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "libraries")
public class Libraries {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "libraries_id")
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Books books;

}





