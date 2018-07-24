package com.vuebackend.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.vuebackend.communication.CreateTokenRequest;
import com.vuebackend.communication.ErrorCause;
import com.vuebackend.communication.ErrorCode;
import com.vuebackend.communication.SuccessResponseMessage;
import com.vuebackend.communication.AddEndpointRequest;
import com.vuebackend.communication.FailureResponseMessage;
import com.vuebackend.communication.ResponseMessage;
import com.vuebackend.dbrepositories.EndpointRepository;
import com.vuebackend.dbrepositories.SensorRepository;
import com.vuebackend.dbrepositories.UserRepository;
import com.vuebackend.entities.Endpoint;
import com.vuebackend.entities.Sensor;
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

    @Autowired SensorRepository sensorRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${authServerAdress}")
    private String authServerAdress;


    @Bean
    public RestTemplate buildRestTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> addEndpoint(@RequestBody AddEndpointRequest request,
                                         @RequestHeader("Authorization") String headerValue) {
        
        Optional<User> user  = this.userRepository.findByUsername(request.getUsername());
        String deviceName = request.getName();
        String[] sensorNames = request.getSensorNames();

        if(user.isPresent() && deviceName != null && !deviceName.isEmpty()
            && sensorNames != null && sensorNames.length > 0) {

            if(this.userRepository.findEndpointByNameOfUser(request.getUsername(), deviceName).isPresent()) {
                return ResponseEntity.ok(new FailureResponseMessage());
            }

            Endpoint newEndpoint;
            String deviceToken = getDeviceToken(request.getUsername(),
                                                request.getName(),
                                                headerValue);

            if(deviceToken == null) {
                return ResponseEntity.ok(new FailureResponseMessage(new ErrorCause(ErrorCode.unknownError)));
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

            for (String sensorName : sensorNames) {
                Sensor sensor = new Sensor(sensorName, newEndpoint);
                this.sensorRepository.save(sensor);
            }

            return ResponseEntity.ok(new SuccessResponseMessage<String>(deviceToken));
        }
        return ResponseEntity.ok(new FailureResponseMessage());
    }

    private String getDeviceToken(String username, String deviceName, String userHeader) {
        Map<String, String> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("deviceName", deviceName);
        
        CreateTokenRequest requestData = new CreateTokenRequest(false).addStringClaims(claims);
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", userHeader);
        headers.set("Content-Type", "application/json");
        HttpEntity<CreateTokenRequest> entity = new HttpEntity<>(requestData, headers);

        String deviceToken = this.restTemplate.exchange(
            authServerAdress + "/endpoint", HttpMethod.POST, entity, String.class).getBody();
        return deviceToken;
    }
}