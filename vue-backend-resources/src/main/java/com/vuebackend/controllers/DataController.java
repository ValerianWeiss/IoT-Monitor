package com.vuebackend.controllers;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("/data")
public class DataController {

    @GetMapping
    public @ResponseBody String getUUID() {
        return UUID.randomUUID().toString();
    }
}