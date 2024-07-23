package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private Boolean isBlocked;

    @Column(length = 6)
    private String activationCode;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isBlocked = false;
    }



}
