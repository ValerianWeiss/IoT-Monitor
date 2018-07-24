package com.vuebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;

    @Value("${simpleBrokerBasePaths}")
    private String[] simpleBrokerBasePaths;

    public void send(String topic, Object payload) {
        for(String simpleBrokerBasePath : simpleBrokerBasePaths) {
            template.convertAndSend(simpleBrokerBasePath + '/' + topic, payload);
        }
    }


}