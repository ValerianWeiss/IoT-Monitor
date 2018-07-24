package com.vuebackend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin
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