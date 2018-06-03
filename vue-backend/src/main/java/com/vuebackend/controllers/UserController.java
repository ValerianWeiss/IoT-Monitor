package com.vuebackend.controllers;

import java.util.Optional;
import java.util.UUID;

import com.vuebackend.communication.messages.ErrorCause;
import com.vuebackend.communication.messages.ErrorCode;
import com.vuebackend.communication.messages.FailureResponseMessage;
import com.vuebackend.communication.messages.LoginRequest;
import com.vuebackend.communication.messages.RegisterRequest;
import com.vuebackend.communication.messages.ResponseMessage;
import com.vuebackend.communication.messages.SuccessResponseMessage;
import com.vuebackend.dbrepositories.SessionRepository;
import com.vuebackend.dbrepositories.UserRepository;
import com.vuebackend.entities.Session;
import com.vuebackend.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "${allowedOrigins}")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;


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
        UUID sessionId = UUID.randomUUID();
        Session session = new Session(user, sessionId.toString());
        this.sessionRepository.save(session);
        return new SuccessResponseMessage(sessionId);
    }


    @PutMapping
    public @ResponseBody ResponseMessage login(@RequestBody LoginRequest loginRequest) {

        Optional<User> user = userRepository.findByUsername(loginRequest.getUsername());
        
        if(user.isPresent()) {
            if(user.get().getPassword().equals(loginRequest.getPassword())) {
                Session session = new Session(user.get(), UUID.randomUUID().toString());
                this.sessionRepository.save(session);
                return new SuccessResponseMessage(UUID.randomUUID());
            } else {
                return new FailureResponseMessage(new ErrorCause(ErrorCode.passwordIncorrect));
            }
        } else {
            return new FailureResponseMessage(new ErrorCause(ErrorCode.usernameIncorrect));
        }
    }

    @GetMapping("/token")
    public @ResponseBody ResponseMessage tokenIsValid(@RequestParam String username, @RequestParam String sessionToken) {
                
        Optional<Session> session = sessionRepository.findBySessionToken(sessionToken);

        if(session.isPresent()) {
            if(session.get().getUser().getUsername().equals(username)) {
                return new SuccessResponseMessage();
            }
        }

        return new FailureResponseMessage(new ErrorCause(ErrorCode.notLoggedIn));
    }
}