package com.example.springboot.model;
import javax.persistence.*;

@Entity
@Table(name = "libraries")
public class Libraries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // to do : user와 book n:n관계

}
