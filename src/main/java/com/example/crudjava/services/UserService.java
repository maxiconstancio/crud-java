package com.example.crudjava.services;

import com.example.crudjava.models.User;
import com.example.crudjava.repositories.UserRepository;
import com.example.crudjava.utils.HashUtil;
import com.example.crudjava.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private HashUtil hash;

    public ResponseEntity search(String token) {


            try {
                String role = jwtUtil.getValue(token);
                if (role.equals("1")) {

                    return ResponseEntity.status(HttpStatus.OK).body((List<User>) repository.findAll());
                }
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
            }

    }




    public ResponseEntity<String>  create(User newUser) {
        List<User> user = repository.findUser(newUser.getEmail());

            if (!user.isEmpty()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Already Exist");
            }

            newUser.setPassword(hash.passHash(newUser.getPassword()));
            repository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Signup Successfully");

    }

    public ResponseEntity<String> delete(String token, Long id) {
        Optional<User> user = repository.findById(id);
        if (!user.isPresent()) {


            if (jwtUtil.getValue(token).equals("1") || String.valueOf(id).equals(jwtUtil.getKey(token))) {
                repository.deleteById(id);
                return ResponseEntity.status(HttpStatus.GONE).body("Deleted Successfully");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
    }



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


            try {
                String role = jwtUtil.getValue(token);
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


}
