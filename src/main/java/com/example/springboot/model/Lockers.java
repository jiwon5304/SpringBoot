package com.example.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "lockers")
public class Lockers {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locker_id")
    private int id;

    @Column(name = "locker_name",nullable = false, length = 100)
    private String name;

    @OneToOne(mappedBy = "locker")
    private Users users;
}
