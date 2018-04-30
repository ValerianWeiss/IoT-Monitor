package com.dbrepositories;

import java.util.Optional;

import com.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    public Optional<User> findByUsername(String username);
}