package com.example.crudjava.controllers;

import com.example.crudjava.models.User;
import com.example.crudjava.services.UserService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> search() { return (List<User>) service.search(); }

    @PostMapping
    public void create(@RequestBody User newUser) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, newUser.getPassword());
        newUser.setPassword(hash);
        service.create(newUser);}

    @PostMapping (value = "/login")
    public void login(@RequestBody User newUser) {

        System.out.println(service.login(newUser));

    }
    @PutMapping
    public void update(@RequestBody User newUser) {service.create(newUser);}



    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable("id") Long id) {service.delete(id);
    }
}
