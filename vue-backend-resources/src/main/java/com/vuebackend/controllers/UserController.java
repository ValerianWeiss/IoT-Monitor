package com.vuebackend.controllers;

import java.util.Iterator;
import java.util.Optional;

import com.vuebackend.communication.GetAllEndpointsResponse;
import com.vuebackend.communication.LoginRequest;
import com.vuebackend.communication.RegisterRequest;
import com.vuebackend.communication.ResponseMessage;
import com.vuebackend.communication.SuccessResponseMessage;
import com.vuebackend.dbrepositories.EndpointRepository;
import com.vuebackend.dbrepositories.UserRepository;
import com.vuebackend.entities.Endpoint;
import com.vuebackend.entities.Sensor;
import com.vuebackend.entities.User;
import com.vuebackend.entitiydata.EndpointData;
import com.vuebackend.entitiydata.SensorData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins = "${allowedOrigins}")
public class UserController {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    EndpointRepository endpointRepository;

    private static int minPasswordLength = 6;
    private static int minUsernameLength = 6;


    @PostMapping("/checkCredentials")
    public ResponseEntity<Boolean> checkCredentials(@RequestBody LoginRequest loginRequest) {
        
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        if (username != null && password != null && 
                !username.isEmpty() && !password.isEmpty()) {

            Optional<User> user = userRepository.findByUsername(username);
            if (user.isPresent()) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                boolean passwordValid =  encoder.matches(password, user.get().getPassword());
                return ResponseEntity.ok(passwordValid);
            }
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody RegisterRequest registerRequest) {
        
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()
            || !registerRequest.passwordsEqual()
            || registerRequest.getPassword().length() < minPasswordLength
            || registerRequest.getUsername().length() < minUsernameLength)  {

            return ResponseEntity.ok(false);
        }

        User user = new User(registerRequest.getUsername(),
                new BCryptPasswordEncoder().encode(registerRequest.getPassword()));

        userRepository.save(user);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{username}/endpoint/all") 
    public ResponseEntity<ResponseMessage> getAllEndpoints(@PathVariable(value="username") String username) {

        Iterator<Endpoint> endpointIterator = this.userRepository.getAllEndpoints(username).iterator();
        GetAllEndpointsResponse response = new GetAllEndpointsResponse();

        while(endpointIterator.hasNext()) {
            Endpoint endpoint = endpointIterator.next();
            response.addEndpoint(new EndpointData(endpoint.getName(),
                                                  endpoint.getDescription(),
                                                  endpoint.getToken()));
        }

        for (EndpointData endpoint : response.getEndpoints()) {
            Iterator<Sensor> sensorIterator = this.endpointRepository.getSensors(username, endpoint.getName()).iterator();
            while(sensorIterator.hasNext()) {
                Sensor sensor = sensorIterator.next();
                endpoint.addSensor(new SensorData(sensor.getName(), sensor.getTopic()));
            }
        }
        return ResponseEntity.ok(new SuccessResponseMessage<GetAllEndpointsResponse>(response));
    }
}