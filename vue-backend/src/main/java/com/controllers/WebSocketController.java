package com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate template;


    public void send(String topic, Object payload) {
        System.out.println("publish hello message");
        template.convertAndSend("/topic" + topic, payload);
    }
}