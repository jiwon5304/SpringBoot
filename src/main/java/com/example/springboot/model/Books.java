package com.example.springboot.model;
import javax.persistence.*;



@Entity
@Table(name = "books")
public class Books {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_title",nullable = false, length = 100)
    private String title;

    @Column(name = "book_category",nullable = false, length = 100)
    private String category;
}