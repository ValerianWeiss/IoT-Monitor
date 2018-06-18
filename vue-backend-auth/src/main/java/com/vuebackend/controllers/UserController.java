package com.vuebackend.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Optional;

import com.vuebackend.entities.User;
import com.vuebackend.communication.ErrorCode;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vuebackend.communication.ErrorCause;
import com.vuebackend.communication.FailureResponseMessage;
import com.vuebackend.communication.ResponseMessage;
import com.vuebackend.communication.LoginRequest;
import com.vuebackend.communication.RegisterRequest;
import com.vuebackend.communication.SuccessResponseMessage;
import com.vuebackend.communication.TokenRequest;
import com.vuebackend.dbrepositories.UserRepository;
import com.vuebackend.security.JWTTokenUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "${allowedOrigins}")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PutMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
            throws IllegalArgumentException, UnsupportedEncodingException {
        
        if (authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
            return ResponseEntity.ok(new SuccessResponseMessage(
                JWTTokenUtils.create(loginRequest.getUsername())));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest)
            throws IllegalArgumentException, UnsupportedEncodingException {

        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new FailureResponseMessage(new ErrorCause(ErrorCode.usernameAlreadyTaken)));
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(new FailureResponseMessage(new ErrorCause(ErrorCode.emailAlreadyTaken)));
        }

        if (registerRequest.passwordsNotEqual()) {
            return ResponseEntity.badRequest()
                    .body(new FailureResponseMessage(new ErrorCause(ErrorCode.passwordsNotEqual)));
        }

        User user = new User(registerRequest.getUsername(),
                new BCryptPasswordEncoder().encode(registerRequest.getPassword()), registerRequest.getEmail());

        userRepository.save(user);
        return login(new LoginRequest(user.getUsername(), registerRequest.getPassword()));
    }

    @PutMapping("/isTokenValid")
    public @ResponseBody ResponseMessage isTokenValid(@RequestBody TokenRequest tokenRequest) 
        throws IllegalArgumentException, UnsupportedEncodingException {
        DecodedJWT jwt = JWTTokenUtils.verify(tokenRequest.getToken());
        String username = jwt.getSubject();

        return userRepository.findByUsername(username).isPresent() ? 
                                    new SuccessResponseMessage() : 
                                    new FailureResponseMessage(new ErrorCause(ErrorCode.notLoggedIn));
    }

    private boolean authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            return encoder.matches(password, user.get().getPassword());
        }
        return false;
    }
}