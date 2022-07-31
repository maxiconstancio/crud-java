package com.example.crudjava.repositories;

import com.example.crudjava.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Long > {
}
