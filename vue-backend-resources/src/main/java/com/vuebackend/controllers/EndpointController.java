package com.vuebackend.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.vuebackend.communication.CreateTokenRequest;
import com.vuebackend.communication.SuccessResponseMessage;
import com.vuebackend.communication.AddEndpointRequest;
import com.vuebackend.communication.FailureResponseMessage;
import com.vuebackend.dbrepositories.EndpointRepository;
import com.vuebackend.dbrepositories.UserRepository;
import com.vuebackend.entities.Endpoint;
import com.vuebackend.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/endpoint")
@CrossOrigin(origins = "${allowedOrigins}")
public class EndpointController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${authServerAdress}")
    private String authServerAdress;


    @Bean
    public RestTemplate buildRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @PostMapping
    public ResponseEntity<?> addEndpoint(@RequestBody AddEndpointRequest request,
                                         @RequestHeader("Authorization") String headerValue) {
        
        Optional<User> user  = userRepository.findByUsername(request.getUsername());
        if(user.isPresent()) {
            Endpoint newEndpoint;
            String deviceToken = getDeviceToken(request.getUsername(),
                                                request.getName(),
                                                headerValue);

            if(deviceToken == null) {
                return ResponseEntity.badRequest().build();
            }
            
            if(request.getDescription() == null) {
                newEndpoint = new Endpoint(user.get(), request.getName(), deviceToken);
            } else {
                newEndpoint = new Endpoint(user.get(),
                                           request.getName(),
                                           deviceToken,
                                           request.getDescription());
            }
            this.endpointRepository.save(newEndpoint);
            return ResponseEntity.ok(new SuccessResponseMessage(deviceToken));
        }
        return ResponseEntity.ok(new FailureResponseMessage());
    }

    private String getDeviceToken(String username, String deviceName, String userHeader) {
        Map<String, String> claims = new HashMap<String, String>();
        claims.put("username", username);
        claims.put("deviceName", deviceName);
        CreateTokenRequest requestData = new CreateTokenRequest().addStringClaims(claims);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", userHeader);
        HttpEntity<CreateTokenRequest> entity = new HttpEntity<>(requestData, headers);
        return this.restTemplate.exchange(authServerAdress + "/endpoint", HttpMethod.GET, entity, String.class).getBody();
    }
}