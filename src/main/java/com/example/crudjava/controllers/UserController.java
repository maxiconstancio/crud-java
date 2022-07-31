package com.example.crudjava.controllers;

import com.example.crudjava.models.User;
import com.example.crudjava.services.UserService;
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
    public void create(@RequestBody User newUser) {service.create(newUser);}

    @PutMapping
    public void update(@RequestBody User newUser) {service.create(newUser);}



    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable("id") Long id) {service.delete(id);
    }
}
