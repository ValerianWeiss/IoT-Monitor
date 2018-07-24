package com.vuebackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.vuebackend.controllers.WebSocketController;
import com.vuebackend.entitiydata.DatapointData;


@Controller
@SuppressWarnings("unused")
public class DatapointController {

    @Autowired
    WebSocketController webSocket;

    @PostMapping("/datapoint")
    public void publishDatapoint(@RequestBody DatapointData datapoint) {
        webSocket.send(datapoint.getTopic(), new Object(){
            public double value = datapoint.getValue();
            public long time = datapoint.getTime();
        });
    }
}