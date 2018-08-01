package com.vuebackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vuebackend.entitiydata.DatapointData;
import com.vuebackend.websocket.WebSocketUtils;


@Controller
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

            @SuppressWarnings("unused")
            public double value = datapoint.getValue();

            @SuppressWarnings("unused")
            public long time = datapoint.getTime();
        });
        return ResponseEntity.ok().build();
    }
}