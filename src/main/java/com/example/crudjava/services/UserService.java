package com.example.crudjava.services;

import com.example.crudjava.models.User;
import com.example.crudjava.repositories.UserRepository;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;




    public List<User> search() { return (List<User>) repository.findAll();}


    public void create(User newUser) { repository.save(newUser);}

    public void delete(Long id) { repository.deleteById(id);}

    public User login(User newUser) {
        List<User> user = repository.findUser(newUser.getEmail());

        if (user.isEmpty()) {
            return null;
        }

        String passwordHashed = user.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, newUser.getPassword())) {
            return user.get(0);
        }
        return null;
    }
}
