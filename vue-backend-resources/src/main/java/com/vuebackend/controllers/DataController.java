package com.vuebackend.controllers;

import java.lang.annotation.Annotation;
import java.util.Optional;

import com.vuebackend.communication.AddDatapointRequest;
import com.vuebackend.dbrepositories.DatapointRepository;
import com.vuebackend.dbrepositories.EndpointRepository;
import com.vuebackend.dbrepositories.UserRepository;
import com.vuebackend.entities.Datapoint;
import com.vuebackend.entities.Endpoint;
import com.vuebackend.entities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller("/data")
public class DataController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DatapointRepository datapointRepository;

    
    @PostMapping
    public ResponseEntity<?> addDataPoint(@RequestBody AddDatapointRequest request) {
        
        Optional<Endpoint> endpoint = userRepository.
                                            findEndpointByNameOfUser(request.getUsername(),
                                                               request.getEndpointName());

        if(endpoint.isPresent()) {
            Datapoint datapoint = new Datapoint(endpoint.get(),
                                                request.getDatapoint().getValue(),
                                                request.getDatapoint().getTime());
            this.datapointRepository.save(datapoint);
        }
        
        return ResponseEntity.ok().build();
    }
}