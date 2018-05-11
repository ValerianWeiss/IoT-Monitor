package com.controllers;

import java.util.Optional;
import java.util.UUID;

import com.communication.messages.ErrorCause;
import com.communication.messages.ErrorCode;
import com.communication.messages.FailureResponseMessage;
import com.communication.messages.LoginRequest;
import com.communication.messages.RegisterRequest;
import com.communication.messages.ResponseMessage;
import com.communication.messages.SuccessResponseMessage;
import com.dbrepositories.UserRepository;
import com.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;


    @PostMapping
    public @ResponseBody ResponseMessage registerUser(@RequestBody RegisterRequest registerRequest) {
        
        if(userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return new FailureResponseMessage(new ErrorCause(ErrorCode.usernameAlreadyTaken)); 
        }

        if(userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return new FailureResponseMessage(new ErrorCause(ErrorCode.emailAlreadyTaken)); 
        }

        if(registerRequest.passwordsNotEqual()) {
            return new FailureResponseMessage(new ErrorCause(ErrorCode.passwordsEqual)); 
        }

        User user = new User(registerRequest.getUsername(),
                             registerRequest.getPassword(),
                             registerRequest.getEmail());
        
        userRepository.save(user);
        return new SuccessResponseMessage(UUID.randomUUID());
    }


    @PutMapping
    public @ResponseBody ResponseMessage login(@RequestBody LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        
        if(user.isPresent()) {
            if(user.get().getPassword().equals(loginRequest.getPassword())) {
                return new SuccessResponseMessage(UUID.randomUUID());
            } else {
                return new FailureResponseMessage(new ErrorCause(ErrorCode.passwordIncorrect));
            }
        } else {
            return new FailureResponseMessage(new ErrorCause(ErrorCode.usernameIncorrect));
        }
    }
}