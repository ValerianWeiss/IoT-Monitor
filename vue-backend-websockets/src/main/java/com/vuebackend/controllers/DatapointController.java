package com.vuebackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vuebackend.entitiydata.DatapointData;


@Controller
@SuppressWarnings("unused")
public class DatapointController {

    @Autowired
    private WebSocketUtils webSocket;

    @Bean
    private WebSocketUtils initWebSocket() {
        return new WebSocketUtils();
    }

    @PutMapping("/datapoint")
    public ResponseEntity<?> publishDatapoint(@RequestBody DatapointData datapoint) {
        webSocket.send(datapoint.getTopic(), new Object(){
            public double value = datapoint.getValue();
            public long time = datapoint.getTime();
        });
        return ResponseEntity.ok().build();
    }
}