package com.example.springboot.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "books")
public class Books {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private int id;

    @Column(name = "book_title",nullable = false, length = 100)
    private String title;

    @Column(name = "book_category",nullable = false, length = 100)
    private String category;

    @OneToMany(mappedBy = "books")
    private List<Libraries> library = new ArrayList<>();

}