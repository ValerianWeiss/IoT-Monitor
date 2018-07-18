package com.vuebackend.controllers;

import com.vuebackend.communication.CreateTokenRequest;
import com.vuebackend.jwt.JWTTokenUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/endpoint")
public class EndpointController {
    
    @PostMapping
    public ResponseEntity<String> getEndpointToken(@RequestBody CreateTokenRequest request) {

        try {
            return ResponseEntity.ok(JWTTokenUtils.create(request, request.canExpire()));
		} catch (Exception e) {
			return ResponseEntity.ok(null);
		}
    }   
}