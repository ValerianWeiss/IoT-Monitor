package com.vuebackend.controllers;

import java.util.Optional;

import com.vuebackend.communication.AddDatapointRequest;
import com.vuebackend.communication.FailureResponseMessage;
import com.vuebackend.communication.ResponseMessage;
import com.vuebackend.communication.SuccessResponseMessage;
import com.vuebackend.dbrepositories.DatapointRepository;
import com.vuebackend.dbrepositories.SensorRepository;
import com.vuebackend.dbrepositories.UserRepository;
import com.vuebackend.entities.Datapoint;
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
@RequestMapping("/data")
@CrossOrigin(origins = "${allowedOrigins}")
public class DataController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatapointRepository datapointRepository;

    @Autowired
    SensorRepository sensorRepository;


    @PostMapping
    public ResponseEntity<ResponseMessage> addDatapoint(@RequestBody AddDatapointRequest request) {
        
        Optional<Endpoint> endpoint = 
                userRepository.findEndpointByNameOfUser(request.getUsername(),
                                                        request.getEndpointName());

        Optional<Sensor> sensor =
                sensorRepository.findByName(request.getEndpointName(), request.getSensorName());

        if(endpoint.isPresent() && sensor.isPresent()) {
            Datapoint datapoint = new Datapoint(sensor.get(),
                                                request.getDatapoint().getValue(),
                                                request.getDatapoint().getTime());
            this.datapointRepository.save(datapoint);
            return ResponseEntity.ok(new SuccessResponseMessage<Object>());
        }
        return ResponseEntity.ok(new FailureResponseMessage());
    }
}