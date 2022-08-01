package com.example.crudjava.utils;

import com.example.crudjava.models.User;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HashUtil {
    @Autowired
    private JWTUtil jwtUtil;
    public String passHash (String userPassword) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        return (argon2.hash(1, 1024, 1, userPassword));

    }

    public ResponseEntity<String> verifyPass (User user, String userPassword) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(user.getPassword(), userPassword)) {

            String token = jwtUtil.create(String.valueOf(user.getId()), (String.valueOf(user.getRole())));

            return  ResponseEntity.status(HttpStatus.ACCEPTED).body(token);
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("password incorrect");

    }
}
