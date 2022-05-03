package com.example.springboot.model;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// Users:Group = N:1
@Entity
@Table(name = "groups")
public class Groups {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private int id;

    @Column(name = "group_name",nullable = false, length = 100)
    private String name;

    // 양방향 연결을 위해 추가
    @OneToMany(mappedBy = "group")
    private List<Users> users = new ArrayList<Users>();

}
