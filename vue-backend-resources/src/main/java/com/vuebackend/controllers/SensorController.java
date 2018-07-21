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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<ResponseMessage> addSensor(@RequestBody AddEndpointRequest request) {
        
        Optional<Endpoint> endpoint = 
                    this.endpointRepository.findByName(request.getUsername(), request.getName());

        String sensorName = request.getName();

        if(endpoint.isPresent() && sensorName != null && !sensorName.isEmpty()
            && sensorRepository.findByName(request.getName(), sensorName).isPresent()) {
                
            Sensor sensor = new Sensor(sensorName, endpoint.get());
            sensorRepository.save(sensor);
            return ResponseEntity.ok(new SuccessResponseMessage<Object>());
        }
        return ResponseEntity.ok(new FailureResponseMessage());
    }
}