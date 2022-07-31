package com.example.crudjava.services;

import com.example.crudjava.models.User;
import com.example.crudjava.repositories.UserRepository;
import com.example.crudjava.utils.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
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


    public List<User> search() { return (List<User>) repository.findAll();}


    public ResponseEntity<String>  create(User newUser, String token) {
        List<User> user = repository.findUser(newUser.getEmail());
        String role = "10";
        if (token != null){
            try {
                role = jwtUtil.getValue(token);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid Token");
            }
        }

       /* if (!user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User Already Exist");
        } */

        if (role.equals("1")) {
            Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String hash = argon2.hash(1, 1024, 1, newUser.getPassword());
            newUser.setPassword(hash);
            repository.save(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body("User Signup Successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("el rol es:" + role);

    }

    public void delete(Long id) { repository.deleteById(id);}

    public ResponseEntity<String> login(User newUser) {

        List<User> user = repository.findUser(newUser.getEmail());

        if (user.isEmpty()) {
            return null;
        }

        String passwordHashed = user.get(0).getPassword();

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, newUser.getPassword())) {

            String token = jwtUtil.create(String.valueOf(user.get(0).getId()), (String.valueOf(user.get(0).getRole())));

            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("password incorrect");
    }
}
