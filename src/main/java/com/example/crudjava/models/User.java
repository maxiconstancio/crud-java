package com.example.crudjava.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString @EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="email")
    private String email;
    @Column(name="firstname")
    private String firstName;
    @Column (name="lastname")
    private String lastName;
    @Column (name="password")
    private String password;



}
