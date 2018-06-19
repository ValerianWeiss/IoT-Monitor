package com.vuebackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import com.vuebackend.controllers.WebSocketController;

@Controller
public class TestController {

    @Autowired
    WebSocketController webSocket;

    @GetMapping("/test")
    public void send() {
        webSocket.send("hello", "Hello World!");
    }
}