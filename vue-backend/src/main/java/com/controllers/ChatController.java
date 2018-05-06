package com.controllers;

import com.messages.MessageSayHello;

import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @SendTo("topic/hello")
    public MessageSayHello sayHello() {
        return new MessageSayHello("Hello World!");
    }
}