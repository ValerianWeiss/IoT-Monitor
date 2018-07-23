package com.vuebackend.websocket;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketOnDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {


    @Override 
    public void onApplicationEvent(SessionDisconnectEvent event) {
        System.out.println("A client disconnected. Total amount of connected clients: "
            + WebSocketOnConnectEvent.getActiveViewCount());
    }
}