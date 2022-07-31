package com.example.crudjava.controllers;

import com.example.crudjava.models.User;
import com.example.crudjava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public List<User> search() { return  service.search(); }

    @PostMapping
    public ResponseEntity<String > create(@RequestHeader(value ="Authorization") String token, @RequestBody User newUser) { return service.create(newUser, null);}

    @PostMapping (value = "/login")
    public ResponseEntity<String> login(@RequestBody User newUser) { return service.login(newUser);}
    @PutMapping
    public ResponseEntity<String> update(@RequestHeader(value ="Authorization") String token, @RequestBody User newUser) {return service.create(newUser, token);}



    @DeleteMapping(value="/{id}")
    public void delete(@PathVariable("id") Long id) {service.delete(id);
    }
}
