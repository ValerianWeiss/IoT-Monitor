package com.controllers;

import com.dbrepositories.UserRepository;
import com.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PutMapping
    public void registerUser(@RequestBody User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            userRepository.save(user);
        }
    }

    @PostMapping
    public @ResponseBody boolean login(@RequestBody User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return true;
        } 
        return false;
    }
}