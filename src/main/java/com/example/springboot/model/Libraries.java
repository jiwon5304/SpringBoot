package com.example.springboot.model;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "libraries")
public class Libraries {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "libraries_id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Books books;

}





