package com.example.springboot.model;
import javax.persistence.*;


@Entity
@Table(name = "users")
public class Users {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name",nullable = false, length = 100)
    private String name;

    @Column(name = "user_password",nullable = false, length = 512)
    private String password;

    // Users:Groups = N:1
    @ManyToOne()
    @JoinColumn(name = "group_id")          // Groups의 id와 연결
    private Groups group;                   // Groups의 mappedBy와 연결

    // Users:Lockers = 1:1
    @OneToOne
    @JoinColumn(name = "locker_id")
    private Lockers locker;

}