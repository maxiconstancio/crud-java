package com.example.crudjava.repositories;

import com.example.crudjava.models.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository <User, Long > {

    @Query ("FROM User WHERE email = :email")
    List<User> findUser(String email);




}
