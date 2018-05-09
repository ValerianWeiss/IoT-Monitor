package com.controllers;

import java.util.Optional;

import com.communication.messages.ErrorCause;
import com.communication.messages.ErrorCode;
import com.communication.messages.FailureResponseMessage;
import com.communication.messages.LoginRequest;
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
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public @ResponseBody boolean registerUser(@RequestBody User user) {
        if(!userRepository.findByUsername(user.getUsername()).isPresent()) {
            userRepository.save(user);
            
            return true;
        }
        return false;
    }


    @PutMapping
    @CrossOrigin(origins = "${allowedOrigins}")
    public @ResponseBody ResponseMessage login(@RequestBody LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        
        if(user.isPresent()) {
            if(user.get().getPassword().equals(loginRequest.getPassword())) {
                return new SuccessResponseMessage();
            } else {
                return new FailureResponseMessage(new ErrorCause(ErrorCode.IncorrectPassword));
            }
        } else {
            return new FailureResponseMessage(new ErrorCause(ErrorCode.IncorrectUsername));
        }
    }
}