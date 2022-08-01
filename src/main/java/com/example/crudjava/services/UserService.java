package com.example.crudjava.services;

import com.example.crudjava.models.User;
import com.example.crudjava.repositories.UserRepository;
import com.example.crudjava.utils.HashUtil;
import com.example.crudjava.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private HashUtil hash;

    public List<User> search() { return (List<User>) repository.findAll();}


    public ResponseEntity<String>  create(User newUser) {
        List<User> user = repository.findUser(newUser.getEmail());

            if (!user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Already Exist");
            }

            newUser.setPassword(hash.passHash(newUser.getPassword()));
            repository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Signup Successfully");

    }

    public void delete(Long id) { repository.deleteById(id);}

    public ResponseEntity<String> login(User newUser) {

        List<User> user = repository.findUser(newUser.getEmail());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User no exist");
        }

        String passwordHashed = user.get(0).getPassword();

        return hash.verifyPass(user.get(0), newUser.getPassword());


    }

    public ResponseEntity<String> update(User newUser, String token) {
        List<User> user = repository.findUser(newUser.getEmail());
        String role ="";
        if (token != null){
            try {
                role = jwtUtil.getValue(token);
                if (role.equals("1") || user.get(0).getEmail().equals(newUser.getEmail()) ) {
                    newUser.setPassword(hash.passHash(newUser.getPassword()));
                    repository.save(newUser);
                    return ResponseEntity.status(HttpStatus.CREATED).body("User Updated Successfully");
                }
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Update Denied" + role + user.get(0).getEmail());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
            }

        }


       return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token is required");
    }
}
