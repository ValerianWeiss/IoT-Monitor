package com.vuebackend.controllers;

import com.vuebackend.jwt.JWTTokenUtils;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/endpoint")
public class EndpointController {
    @GetMapping
    public ResponseEntity<String> getEndpointToken(@RequestBody String subjectName) {
        try {
			return ResponseEntity.ok(JWTTokenUtils.create(subjectName, false));
		} catch (Exception e) {
			return ResponseEntity.ok(null);
		}
    }   
}