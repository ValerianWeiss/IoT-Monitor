package com.vuebackend.controllers;

import com.vuebackend.entitiydata.DatapointData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings("unused")
@Controller("/datapoint")
@CrossOrigin("${allowedOrigins}")
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Value("${simpleBrokerBasePaths}")
    private String[] simpleBrokerBasePaths;

    @PutMapping
    public void publishDatapoint(@RequestBody DatapointData datapoint) {
        this.sendDatapoint(datapoint.getTopic(), new Object() {
            public double value = datapoint.getValue();
            public long time = datapoint.getTime();
        });
    }

    private void sendDatapoint(String topic, Object payload) {
        for(String simpleBrokerBasePath : simpleBrokerBasePaths) {
            template.convertAndSend(simpleBrokerBasePath + '/' + topic, payload);
        }
    }


}