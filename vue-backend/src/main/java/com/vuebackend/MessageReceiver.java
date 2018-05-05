package com.vuebackend;

import org.springframework.stereotype.Component;

@Component
public class MessageReceiver {

    private String message;

    public String getMessage() {
        return message;
    }

    public void receivedMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}