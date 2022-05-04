package com.example.springboot.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name",nullable = false, length = 100)
    private String name;

    @Column(name = "user_password",nullable = false, length = 512)
    private String password;

    // Users:Groups = N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")          // Groups의 id와 연결
    private Groups group;                   // Groups의 mappedBy와 연결

    // Users:Lockers = 1:1
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "locker_id")
    private Lockers locker;

    // Users:Books = N : N
    @OneToMany(mappedBy = "users")
    private List<Libraries> library = new ArrayList<>();

}