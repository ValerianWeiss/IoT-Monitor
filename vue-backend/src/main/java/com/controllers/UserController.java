package com.controllers;

import com.dbrepositories.UserRepository;
import com.entities.User;
import com.vuebackend.MessageReceiver;
import com.vuebackend.MessageSender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private MessageSender sender;


    @PutMapping
    public @ResponseBody boolean registerUser(@RequestBody User user) {
        if(!userRepository.findByUsername(user.getUsername()).isPresent()) {
            userRepository.save(user);
            
            return true;
        }
        return false;
    }

    @PostMapping
    public @ResponseBody boolean login(@RequestBody User user) {
        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            return true;
        } 
        return false;
    }

    @GetMapping
    public void send() {
        sender.send();
    }
}