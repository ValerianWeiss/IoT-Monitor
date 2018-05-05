package com.vuebackend;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {         
        rabbitTemplate.convertAndSend("posts", "topic", "Hello from RabbitMQ!");
        System.out.println("Send message!");
    }
}