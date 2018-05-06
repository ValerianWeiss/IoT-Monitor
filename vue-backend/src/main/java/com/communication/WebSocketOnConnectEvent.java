package com.communication;

import com.controllers.ChatController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.web.socket.messaging.SessionConnectEvent;

public class WebSocketOnConnectEvent implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    private ChatController controller;

    public void onApplicationEvent(SessionConnectEvent event) {
        System.out.println("A view connected: " + event.getMessage());
        controller.sayHello();
    }
}