package com.example.springboot.model;
import javax.persistence.*;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name",nullable = false, length = 100)
    private String name;

    @Column(name = "user_password",nullable = false, length = 512)
    private String password;
}