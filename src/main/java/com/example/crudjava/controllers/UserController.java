package com.example.crudjava.controllers;

import com.example.crudjava.models.User;
import com.example.crudjava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@ResponseBody
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<String> search(@RequestHeader(value = "Authorization") String token) { return (ResponseEntity<String>) service.search(token); }

    @PostMapping
    public ResponseEntity<String > create(@RequestBody User newUser) {
        return service.create(newUser);}

    @PostMapping (value = "/login")
    public ResponseEntity<String> login(@RequestBody User newUser) { return service.login(newUser);}


   @PutMapping
    public ResponseEntity<String> update(@RequestHeader(value ="Authorization") String token,
                                         @RequestBody User newUser) {
        return service.update(newUser, token);}


    @DeleteMapping(value="/{id}")
    public ResponseEntity<String> delete(@RequestHeader (value = "Authorization") String token, @PathVariable("id") Long id) { return service.delete(token, id);
    }
}
