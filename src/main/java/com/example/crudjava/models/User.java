package com.example.crudjava.models;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "user")
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
@Getter
@Setter
@ToString @EqualsAndHashCode
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name="id")
    private Long id;
    @Column( nullable = false, name="email")
    private String email;
    @Column(name="firstname")
    private String firstName;
    @Column (name="lastname")
    private String lastName;
    @Column (name="password")
    private String password;
    @Column (name = "role")
    private int role = 2;
    @Column (name = "deleted")
    private boolean deleted = Boolean.FALSE;



}
