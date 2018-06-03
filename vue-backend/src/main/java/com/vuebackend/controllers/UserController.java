package com.vuebackend.controllers;

import java.io.UnsupportedEncodingException;
import java.util.Objects;
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
import com.vuebackend.security.JWTSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "${allowedOrigins}")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @PutMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());
        return (ResponseEntity<?>) ResponseEntity.ok(getToken(loginRequest.getUsername()));
    }

    private String getToken(@RequestParam String issuer) {
        try {
			return JWTSecurity.createJWTToken(issuer);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            System.out.println("could not authenticate user");
        }
    }
}