package com.example.crudjava.services;

import com.example.crudjava.models.User;
import com.example.crudjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;



    // findAll
    public List<User> search() { return (List<User>) repository.findAll();}

    public void create(User newUser) { repository.save(newUser);}

    public void delete(Long id) { repository.deleteById(id);}
}
