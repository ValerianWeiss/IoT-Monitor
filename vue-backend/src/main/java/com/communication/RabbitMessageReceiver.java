package com.communication;

import org.springframework.stereotype.Component;

@Component
public class RabbitMessageReceiver {

    private String message;

    
    public String getMessage() {
        return message;
    }

	public void onMessage(String message) {
        System.out.println("got a message");
        System.out.println("Received <" + message + ">");
	}
}