package com.vuebackend.controllers;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.vuebackend.jwt.JWTTokenUtils;
import com.vuebackend.communication.CreateTokenRequest;
import com.vuebackend.communication.ErrorCause;
import com.vuebackend.communication.ErrorCode;
import com.vuebackend.communication.FailureResponseMessage;
import com.vuebackend.communication.LoginRequest;
import com.vuebackend.communication.RegisterRequest;
import com.vuebackend.communication.SuccessResponseMessage;
import com.vuebackend.communication.TokenRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "${allowedOrigins}")
public class UserController {

    private RestTemplate restTemplate = new RestTemplate();
    @Value("${resourceServer}")
    private String resourceServerAdress;


    @PutMapping
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
            throws IllegalArgumentException, UnsupportedEncodingException {
        boolean isValid = this.restTemplate.postForObject(
                                this.resourceServerAdress + "user/checkCredentials", loginRequest, Boolean.class);

        if(isValid) {
            CreateTokenRequest tokenRequest = this.addUsernameClaim(loginRequest.getUsername());
            return ResponseEntity.ok(new SuccessResponseMessage(JWTTokenUtils.create(tokenRequest, true)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest)
            throws IllegalArgumentException, UnsupportedEncodingException {

        boolean registrationSuccessful = restTemplate.postForObject(
                    this.resourceServerAdress + "user/register", registerRequest, Boolean.class);
        
        if(registrationSuccessful) {
            return login(new LoginRequest(registerRequest.getUsername(), registerRequest.getPassword()));
        }
        return ResponseEntity.ok(new FailureResponseMessage(new ErrorCause(ErrorCode.unknownError)));
    }

    @PostMapping("/isTokenValid")
    public ResponseEntity<Boolean> isTokenValid(@RequestBody TokenRequest tokenRequest)
            throws IllegalArgumentException, UnsupportedEncodingException {
       
        try {
            JWTTokenUtils.verify(tokenRequest.getToken());
        } catch(Exception e) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    private CreateTokenRequest addUsernameClaim(String username) {
        CreateTokenRequest tokenRequest = new CreateTokenRequest();
        Map<String, String> claims = new HashMap<String, String>();
        claims.put("username", username);
        tokenRequest.addStringClaims(claims);
        return tokenRequest;
    }
}