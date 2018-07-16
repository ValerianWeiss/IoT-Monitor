package com.vuebackend.controllers;

import java.util.Optional;

import com.vuebackend.communication.LoginRequest;
import com.vuebackend.communication.RegisterRequest;
import com.vuebackend.dbrepositories.UserRepository;
import com.vuebackend.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "${allowedOrigins}")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;


    @PostMapping("/checkCredentials")
    public ResponseEntity<Boolean> userDataValid(@RequestBody LoginRequest loginRequest) {
        
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username != null && password != null && 
                !username.isEmpty() && !password.isEmpty()) {

            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                boolean passwordValid =  encoder.matches(password, user.get().getPassword());
                return ResponseEntity.ok(passwordValid);
            }
            return ResponseEntity.ok(false);
        }

        return ResponseEntity.ok(true);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody RegisterRequest registerRequest) {
        
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent() ||
                registerRequest.passwordsNotEqual()) {
            return ResponseEntity.ok(false);
        }

        User user = new User(registerRequest.getUsername(),
                new BCryptPasswordEncoder().encode(registerRequest.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok(true);
    }
}