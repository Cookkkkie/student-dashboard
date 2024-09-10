package com.example.main;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID")
    private Long id;

    private String name;
    private String surname;
    private String email;
    private String password;

    // Getters and Setters

    public void setId(Long id) {
        this.id = id;
    }
}
