package com.vuebackend.controllers;

import java.util.Optional;

import com.vuebackend.communication.AddEndpointRequest;
import com.vuebackend.communication.FailureResponseMessage;
import com.vuebackend.communication.ResponseMessage;
import com.vuebackend.communication.SuccessResponseMessage;
import com.vuebackend.dbrepositories.EndpointRepository;
import com.vuebackend.dbrepositories.SensorRepository;
import com.vuebackend.entities.Endpoint;
import com.vuebackend.entities.Sensor;
import com.vuebackend.security.JwtTokenClaimUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sensor")
@CrossOrigin(origins = "${allowedOrigins}")
public class SensorController {

    @Autowired
    private EndpointRepository endpointRepository;

    @Autowired
    private SensorRepository sensorRepository;


    @PostMapping
    public ResponseEntity<ResponseMessage> addSensors(@RequestBody AddEndpointRequest request,
                                                      @RequestHeader("Authorization") String token) {
        
        String username = JwtTokenClaimUtils.getUsername(token);
        String [] sensorNames = request.getSensorNames();
        String endpointName = request.getEndpointName();
        
        Optional<Endpoint> endpoint = this.endpointRepository.findByName(username, endpointName);

        if(endpoint.isPresent() && sensorNames != null) {
            
            for (String sensorName : sensorNames) {
                Sensor sensor = new Sensor(sensorName, endpoint.get());
                sensorRepository.save(sensor);    
            }
            return ResponseEntity.ok(new SuccessResponseMessage<>());
        }
        return ResponseEntity.ok(new FailureResponseMessage());
    }

    @DeleteMapping("/{endpointName}/{sensorName}")
    public ResponseEntity<ResponseMessage> deleteSensor(@PathVariable(value="endpointName") String endpointName,
                                                        @PathVariable(value="sensorName") String sensorName,
                                                        @RequestHeader("Authorization") String token) {
        
        String username = JwtTokenClaimUtils.getUsername(token);                                                                        
        Optional<Sensor> sensor = this.sensorRepository.findByName(username, endpointName, sensorName);

        if(sensor.isPresent()) {
            this.sensorRepository.delete(sensor.get());
            return ResponseEntity.ok(new SuccessResponseMessage<>());
        }
        return ResponseEntity.ok(new FailureResponseMessage());
    }
}